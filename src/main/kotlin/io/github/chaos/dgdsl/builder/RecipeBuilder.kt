package io.github.chaos.dgdsl.builder

import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import java.util.function.Consumer

class RecipeBuilder(private val builder: ShapedRecipeBuilder, private val result: IItemProvider) {
    fun pattern(patternLines: () -> String) {
        val patterns = patternLines.invoke().replace("\\s".toRegex(), "").chunked(3)

        for (pattern in patterns)
            builder.patternLine(pattern)
    }

    fun key(keys: RecipeKeyBuilder.() -> Unit) {
        val keyMap = RecipeKeyBuilder().apply(keys).build()

        keyMap.forEach(builder::key)
    }

    fun group(group: String) {
        builder.setGroup(group)
    }

    fun criterion(criterions: CriterionBuilder.() -> Unit) {
        val criterionMap = CriterionBuilder().apply(criterions).build()

        criterionMap.forEach(builder::addCriterion)
    }

    fun build(
        consumer: Consumer<IFinishedRecipe>
    ) = builder.build(consumer)

    fun build(
        consumer: Consumer<IFinishedRecipe>,
        id: ResourceLocation
    ) = builder.build(consumer, id)

    fun build(
        consumer: Consumer<IFinishedRecipe>,
        save: String
    ) = builder.build(consumer, save)
}