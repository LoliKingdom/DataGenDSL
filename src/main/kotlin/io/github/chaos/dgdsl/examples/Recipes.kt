package io.github.chaos.dgdsl.examples

import io.github.chaos.dgdsl.DataGeneratorDSL
import io.github.chaos.dgdsl.recipe
import net.minecraft.block.Blocks
import net.minecraft.data.DataGenerator
import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.RecipeProvider
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.item.Items
import java.util.function.Consumer

class Recipes(generatorIn: DataGenerator) : RecipeProvider(generatorIn) {
    override fun registerRecipes(consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shapedRecipe(Blocks.DIAMOND_BLOCK, 64)
            .patternLine("XXX")
            .patternLine("XXX")
            .patternLine("XXX")
            .key('X', Blocks.DIRT)
            .setGroup("")
            .addCriterion("has_dirt", hasItem(Blocks.DIRT))
            .build(consumer)

        recipe(Blocks.GRASS_BLOCK) {
            pattern {
                """
                    xxx
                    xyx
                    xxx
                """
            }

            itemKey {
                Pair('x', Items.BONE)
                Pair('y', Blocks.BONE_BLOCK.asItem())
            }

            group {
                DataGeneratorDSL.MODID
            }
        }.build(consumer)
    }
}