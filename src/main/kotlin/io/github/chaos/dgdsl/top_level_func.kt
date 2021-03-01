package io.github.chaos.dgdsl

import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.util.IItemProvider
import net.minecraftforge.common.crafting.ConditionalRecipe

fun recipe(result: IItemProvider, resultCount: Int = 1, builder: RecipeBuilder.() -> Unit): RecipeBuilder =
    RecipeBuilder(ShapedRecipeBuilder(result, resultCount), result).apply(builder)

fun conditionalRecipe(builder: ConditionalRecipeBuilder.() -> Unit): ConditionalRecipeBuilder =
    ConditionalRecipeBuilder().apply(builder)