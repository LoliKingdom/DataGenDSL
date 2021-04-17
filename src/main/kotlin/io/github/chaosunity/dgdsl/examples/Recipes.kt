package io.github.chaosunity.dgdsl.examples

import io.github.chaosunity.dgdsl.*
import io.github.chaosunity.dgdsl.builder.AdvancementBuilder
import net.minecraft.advancements.FrameType
import net.minecraft.block.Blocks
import net.minecraft.data.DataGenerator
import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.RecipeProvider
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.StringTextComponent
import java.util.function.Consumer

class Recipes(generatorIn: DataGenerator) : RecipeProvider(generatorIn) {
    override fun buildShapelessRecipes(consumer: Consumer<IFinishedRecipe>) {
        val ID = ResourceLocation("data_gen_dsl_test", "conditional")

        conditionalRecipe {
            condition {
                and {
                    not(modLoaded("minecraft")) +
                    itemExists {
                          "minecraft" to "dirt"
                    } +
                    False
                }
            }

            recipe {
                recipe(Blocks.DIAMOND_BLOCK, 64) {
                    pattern {
                        """
                            xxx
                            xxx
                            xxx
                        """
                    }

                    key {
                        'x' to Blocks.DIRT
                    }

                    group("")

                    criterion {
                        "has_dirt" to has(Blocks.DIRT)
                    }
                }.build(this)
            }

            advancement(ID) {
                condition {
                    and {
                        not(modLoaded("minecraft")) +
                        itemExists {
                              "minecraft" to "dirt"
                        } +
                        False
                    }
                }

                advancement {
                    parentID {
                        "minecraft" to "root"
                    }

                    display {
                        AdvancementBuilder.DisplayInfo(
                            Blocks.DIAMOND_BLOCK,
                            StringTextComponent("Dirt2Diamonds"),
                            StringTextComponent("The BEST crafting recipe in the game!"),
                            null,
                            FrameType.TASK,
                            false,
                            false,
                            false
                        )
                    }
                }
            }
        }.build(consumer, ID)

        conditionalRecipe {
            condition {
                and {
                    not(modLoaded("minecraft")) +
                    itemExists {
                          "minecraft" to "dirt"
                    } +
                    False
                }
            }

            recipe {
                recipe(Blocks.DIAMOND_BLOCK, 64) {
                    pattern {
                        """
                            xxx
                            xxx
                            xxx
                        """
                    }

                    key {
                        'x' to Blocks.DIRT
                    }

                    group("")

                    criterion {
                        "has_dirt" to has(Blocks.DIRT)
                    }
                }.build(this)
            }
        }.genAdvancement().build(consumer) {
            "data_gen_dsl_test" to "conditional2"
        }
    }
}