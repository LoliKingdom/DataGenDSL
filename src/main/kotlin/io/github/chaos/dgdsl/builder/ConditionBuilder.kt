package io.github.chaos.dgdsl.builder

import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.crafting.conditions.*

class ConditionBuilder {
    val True = TrueCondition.INSTANCE
    val False = FalseCondition.INSTANCE

    fun not(not: ICondition): NotCondition =
        NotCondition(not)

    fun not(not: () -> ICondition): NotCondition =
        not(not.invoke())

    fun and(and: And.() -> List<ICondition>): AndCondition =
        And.parse(and.invoke(And()))

    fun or(or: Or.() -> List<ICondition>): OrCondition =
        Or.parse(or.invoke(Or()))

    fun itemExists(location: String): ItemExistsCondition =
        ItemExistsCondition(location)

    fun itemExists(identifier: ResourceLocation): ItemExistsCondition =
        ItemExistsCondition(identifier)

    fun itemExists(idList: ItemExists.() -> Unit): ICondition =
        ItemExists().apply(idList).parse()

    fun modLoaded(modID: String): ModLoadedCondition =
        ModLoadedCondition(modID)

    fun modLoaded(modIDs: ModLoaded.() -> Unit): ICondition =
        ModLoaded().apply(modIDs).parse()

    interface IFactorParser {
        operator fun ICondition.plus(condition: ICondition): MutableList<ICondition> =
            mutableListOf(this, condition)

        operator fun ICondition.unaryPlus(): MutableList<ICondition> =
            mutableListOf(this)
    }

    class And : IFactorParser {
        companion object {
            fun parse(conditions: List<ICondition>): AndCondition =
                AndCondition(*conditions.toTypedArray())
        }
    }

    class Or : IFactorParser {
        companion object {
            fun parse(conditions: List<ICondition>): OrCondition =
                OrCondition(*conditions.toTypedArray())
        }
    }

    inner class ItemExists {
        private val itemIDs = mutableListOf<ResourceLocation>()

        operator fun String.unaryPlus() {
            itemIDs += ResourceLocation(this)
        }

        infix fun String.to(path: String) {
            itemIDs += ResourceLocation(this, path)
        }

        fun parse(): ICondition =
            if (itemIDs.size == 1) {
                ItemExistsCondition(itemIDs.first())
            } else {
                AndCondition(*itemIDs.map(::ItemExistsCondition).toTypedArray())
            }
    }

    inner class ModLoaded {
        private val modIDs = mutableListOf<String>()

        operator fun String.unaryPlus() {
            modIDs += this
        }

        fun parse(): ICondition =
            if (modIDs.size == 1) {
                ModLoadedCondition(modIDs.first())
            } else {
                AndCondition(*modIDs.map(::ModLoadedCondition).toTypedArray())
            }
    }
}