package io.github.chaos.dgdsl

import io.github.chaos.dgdsl.builder.ConditionalRecipeBuilder
import io.github.chaos.dgdsl.builder.LootPoolBuilder
import io.github.chaos.dgdsl.builder.RecipeBuilder
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.loot.LootPool
import net.minecraft.util.IItemProvider

fun recipe(result: IItemProvider, resultCount: Int = 1, builder: RecipeBuilder.() -> Unit): RecipeBuilder =
    RecipeBuilder(ShapedRecipeBuilder(result, resultCount), result).apply(builder)

fun conditionalRecipe(builder: ConditionalRecipeBuilder.() -> Unit): ConditionalRecipeBuilder =
    ConditionalRecipeBuilder().apply(builder)

fun lootPool(builder: LootPoolBuilder.() -> Unit): LootPool.Builder =
    LootPoolBuilder(LootPool.builder()).apply(builder).raw()