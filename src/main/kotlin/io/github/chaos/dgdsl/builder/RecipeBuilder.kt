package io.github.chaos.dgdsl.builder

import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import java.util.function.Consumer

class RecipeBuilder(private val builder: ShapedRecipeBuilder, private val result: IItemProvider) : AbstractBuilder() {
    fun pattern(patternLines: () -> String) {
        val patterns = patternLines.invoke().replace("\\s".toRegex(), "").chunked(3)

        for (pattern in patterns)
            builder.pattern(pattern)
    }

    fun key(keys: RecipeKeyBuilder.() -> Unit) {
        val keyMap = RecipeKeyBuilder().apply(keys).build()

        keyMap.forEach(builder::define)
    }

    fun group(group: String) {
        builder.group(group)
    }

    fun criterion(criterions: CriterionBuilder.() -> Unit) {
        val criterionMap = CriterionBuilder().apply(criterions).build()

        criterionMap.forEach(builder::unlockedBy)
    }

    fun build(
        consumer: Consumer<IFinishedRecipe>
    ) = builder.save(consumer)

    fun build(
        consumer: Consumer<IFinishedRecipe>,
        id: ResourceLocation
    ) = builder.save(consumer, id)

    fun build(
        consumer: Consumer<IFinishedRecipe>,
        save: String
    ) = builder.save(consumer, save)
}