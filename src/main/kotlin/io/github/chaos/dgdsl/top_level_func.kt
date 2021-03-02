package io.github.chaos.dgdsl

import io.github.chaos.dgdsl.builder.ConditionalRecipeBuilder
import io.github.chaos.dgdsl.builder.RecipeBuilder
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.util.IItemProvider

fun recipe(result: IItemProvider, resultCount: Int = 1, builder: RecipeBuilder.() -> Unit): RecipeBuilder =
    RecipeBuilder(ShapedRecipeBuilder(result, resultCount), result).apply(builder)

fun conditionalRecipe(builder: ConditionalRecipeBuilder.() -> Unit): ConditionalRecipeBuilder =
    ConditionalRecipeBuilder().apply(builder)