package io.github.chaos.dgdsl.examples

import io.github.chaos.dgdsl.*
import net.minecraft.block.Blocks
import net.minecraft.data.DataGenerator
import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.RecipeProvider
import net.minecraft.item.Items
import java.util.function.Consumer

class Recipes(generatorIn: DataGenerator) : RecipeProvider(generatorIn) {
    override fun registerRecipes(consumer: Consumer<IFinishedRecipe>) {
        conditionalRecipe {
            condition {
                and {
                    True with
                    False with
                    ItemExists("minecraft", "bone_meal") with
                    or {
                        ModLoaded("eki_lib") with
                        False
                    }
                }
            }
        }

        recipe(Blocks.GRASS_BLOCK) {
            pattern {
                """
                    xxx
                    xyx
                    xxx
                """
            }

            key {
                'x' to Items.BONE
                'y' to Items.BONE_BLOCK
            }

            group("")

            criterion {
                "has_dirt" to hasItem(Blocks.DIRT)
            }
        }.build(consumer)
    }
}