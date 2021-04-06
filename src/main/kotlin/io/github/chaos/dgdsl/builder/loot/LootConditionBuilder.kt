package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.builder.AbstractBuilder
import io.github.chaos.dgdsl.builder.loot.conditions.ILootConditionCollector
import io.github.chaos.dgdsl.builder.utils.IConcentrator2List
import io.github.chaos.dgdsl.builder.utils.IListInfixFunctions
import net.minecraft.advancements.criterion.DamageSourcePredicate
import net.minecraft.advancements.criterion.StatePropertiesPredicate
import net.minecraft.block.Block
import net.minecraft.loot.conditions.Alternative
import net.minecraft.loot.conditions.BlockStateProperty
import net.minecraft.loot.conditions.DamageSourceProperties
import net.minecraft.loot.conditions.ILootCondition

class LootConditionBuilder : AbstractBuilder(), IListInfixFunctions {
    private val conditions = mutableListOf<ILootCondition.IBuilder>()

    /**
     *  Alternative
     */

    fun alternative(builder: AlternativeCollector.() -> Unit) =
        conditions add AlternativeCollector().apply(builder).collect()

    inner class AlternativeCollector : ILootConditionCollector, IConcentrator2List<ILootCondition.IBuilder> {
        private val builder = Alternative.alternative()
        private val conditions = mutableListOf<ILootCondition.IBuilder>()

        fun alternative(conditions: IConcentrator2List<ILootCondition.IBuilder>.() -> List<ILootCondition.IBuilder>) =
            conditions.invoke(IConcentrator2List.gen()).forEach(this.conditions::add)

        override fun collect(): ILootCondition.IBuilder =
            builder.apply { conditions.forEach(::or) }
    }

    /**
     *  BlockState Property
     */

    fun blockStateProperty(block: Block) =
        conditions add BlockStateProperty.hasBlockStateProperties(block)

    fun blockStateProperty(block: Block, property: StatePropertiesPredicate.Builder) =
        conditions add BlockStateProperty.hasBlockStateProperties(block).setProperties(property)

    /**
     *  Damage Source Properties
     */

    fun damageSourceProperty(builder: DamageSourcePredicate.Builder) =
        conditions add DamageSourceProperties.hasDamageSource(builder)

    /**
     *
     */
}