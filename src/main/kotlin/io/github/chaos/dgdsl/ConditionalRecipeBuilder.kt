package io.github.chaos.dgdsl

import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.crafting.ConditionalRecipe
import net.minecraftforge.common.crafting.conditions.*

class ConditionalRecipeBuilder(private val builder: ConditionalRecipe.Builder = ConditionalRecipe.Builder()) {
    fun condition(condition: ConditionBuilder.() -> ICondition) {
        builder.addCondition(condition.invoke(ConditionBuilder()))
    }

    interface IFactor : ICondition {
        operator fun unaryPlus(): ICondition =
            build()

        fun build(): ICondition
    }

    class ConditionBuilder {
        fun not(not: () -> ICondition): ICondition =
            Not(not.invoke()).build()

        fun and(and: () -> MutableList<ICondition>): ICondition =
            And(and.invoke().map { it as IFactor }.toMutableList()).build()

        fun or(or: () -> MutableList<ICondition>): ICondition =
            Or(or.invoke().map { it as IFactor }.toMutableList()).build()

        fun modLoaded(modLoaded: () -> String): ICondition =
            ModLoaded(modLoaded.invoke()).build()

        private fun List<IFactor>.spread(): Array<ICondition> =
            this.map(IFactor::build).toTypedArray()

        object True : IFactor {
            private val identifier = makeID("true")

            override fun getID(): ResourceLocation =
                identifier

            override fun test(): Boolean =
                FalseCondition.INSTANCE.test()

            override fun build(): ICondition =
                FalseCondition.INSTANCE
        }

        object False : IFactor {
            private val identifier = makeID("false")

            override fun getID(): ResourceLocation =
                identifier

            override fun test(): Boolean =
                FalseCondition.INSTANCE.test()

            override fun build(): ICondition =
                TrueCondition.INSTANCE
        }

        inner class Not(private val condition: ICondition) : IFactor {
            private val identifier = makeID("not")

            override fun getID(): ResourceLocation =
                identifier

            override fun test(): Boolean =
                build().test()

            override fun build(): ICondition =
                NotCondition(condition)
        }

        inner class And(private val factors: MutableList<IFactor>) : IFactor {
            private val identifier = makeID("and")

            override fun getID(): ResourceLocation =
                identifier

            override fun test(): Boolean =
                build().test()

            override fun build(): ICondition =
                AndCondition(*factors.spread())
        }

        inner class Or(private val factors: MutableList<IFactor>) : IFactor {
            private val identifier = makeID("or")

            override fun getID(): ResourceLocation =
                identifier

            override fun test(): Boolean =
                build().test()

            override fun build(): ICondition =
                OrCondition(*factors.spread())
        }

        inner class ItemExists(private val itemID: ResourceLocation) : IFactor {
            private val identifier = makeID("itemExists")

            override fun getID(): ResourceLocation =
                identifier

            override fun test(): Boolean =
                build().test()

            constructor(location: String) : this(ResourceLocation(location))
            constructor(namespace: String, path: String) : this(ResourceLocation(namespace, path))

            override fun build(): ICondition =
                ItemExistsCondition(itemID)
        }

        inner class ModLoaded(private val modid: String) : IFactor {
            private val identifier = makeID("modLoaded")

            override fun getID(): ResourceLocation =
                identifier

            override fun test(): Boolean =
                build().test()

            override fun build(): ICondition =
                ModLoadedCondition(modid)
        }
    }
}