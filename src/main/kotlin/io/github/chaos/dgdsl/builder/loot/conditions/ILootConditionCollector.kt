package io.github.chaos.dgdsl.builder.loot.conditions

import net.minecraft.loot.conditions.ILootCondition

interface ILootConditionCollector : ILootCondition.IBuilder {
    fun collect(): ILootCondition.IBuilder

    override fun build(): ILootCondition =
        collect().build()
}