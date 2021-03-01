package io.github.chaos.dgdsl

import net.minecraft.block.Block
import net.minecraft.data.ShapedRecipeBuilder

fun recipe(block: Block, resultCount: Int = 1, builder: RecipeBuilder.() -> Unit): RecipeBuilder =
    RecipeBuilder(ShapedRecipeBuilder.shapedRecipe(block, resultCount)).apply(builder)