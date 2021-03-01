package io.github.chaos.dgdsl

import net.minecraftforge.common.crafting.conditions.ICondition

typealias True = ConditionalRecipeBuilder.ConditionBuilder.True
typealias False = ConditionalRecipeBuilder.ConditionBuilder.False
typealias And = ConditionalRecipeBuilder.ConditionBuilder.And
typealias Or = ConditionalRecipeBuilder.ConditionBuilder.Or
typealias Not = ConditionalRecipeBuilder.ConditionBuilder.Not
typealias ItemExists = ConditionalRecipeBuilder.ConditionBuilder.ItemExists
typealias ModLoaded = ConditionalRecipeBuilder.ConditionBuilder.ModLoaded

infix fun ConditionalRecipeBuilder.IFactor.with(anotherFactor: ConditionalRecipeBuilder.IFactor): MutableList<ICondition> =
    mutableListOf(this, anotherFactor)

infix fun MutableList<ICondition>.with(anotherCondition: ICondition): MutableList<ICondition> =
    this.plus(anotherCondition).toMutableList()

infix fun MutableList<ICondition>.with(anotherFactor: ConditionalRecipeBuilder.IFactor): MutableList<ICondition> =
    this.plus(anotherFactor).toMutableList()