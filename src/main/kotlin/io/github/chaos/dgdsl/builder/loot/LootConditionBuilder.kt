package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.builder.loot.conditions.ILootConditionCollector
import io.github.chaos.dgdsl.builder.utils.IConcentrator2List
import io.github.chaos.dgdsl.builder.utils.IListInfixFunctions
import net.minecraft.loot.conditions.Alternative
import net.minecraft.loot.conditions.ILootCondition

class LootConditionBuilder : IListInfixFunctions {
    private val conditions = mutableListOf<ILootCondition.IBuilder>()

    /**
     *  Alternative
     */

    fun alternative(builder: AlternativeCollector.() -> Unit) =
        conditions add AlternativeCollector().apply(builder).collect()

    inner class AlternativeCollector : ILootConditionCollector, IConcentrator2List<ILootCondition.IBuilder> {
        private val builder = Alternative.builder()
        private val conditions = mutableListOf<ILootCondition.IBuilder>()

        fun alternative(conditions: IConcentrator2List<ILootCondition.IBuilder>.() -> List<ILootCondition.IBuilder>) =
            conditions.invoke(IConcentrator2List.gen()).forEach(this.conditions::add)

        override fun collect(): ILootCondition.IBuilder =
            builder.apply { conditions.forEach(::alternative) }
    }
}