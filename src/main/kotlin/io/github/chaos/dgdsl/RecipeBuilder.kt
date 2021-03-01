package io.github.chaos.dgdsl

import net.minecraft.advancements.ICriterionInstance
import net.minecraft.advancements.ICriterionTrigger
import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.item.Item
import net.minecraft.item.crafting.Ingredient
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry
import java.util.*
import java.util.function.Consumer

class RecipeBuilder(private val builder: ShapedRecipeBuilder, private val result: IItemProvider) {
    fun pattern(patternLines: () -> String) {
        val patterns = patternLines.invoke().replace("\\s".toRegex(), "").chunked(3)

        for (pattern in patterns)
            builder.patternLine(pattern)
    }

    fun key(keys: KeyBuilder.() -> Unit) {
        val keyMap = KeyBuilder().apply(keys).build()

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
        consumer: Consumer<IFinishedRecipe>,
        id: ResourceLocation = Registry.ITEM.getKey(result.asItem())
    ) = builder.build(consumer, id)

    fun build(
        consumer: Consumer<IFinishedRecipe>,
        save: String
    ) = builder.build(consumer, save)

    class KeyBuilder {
        private val keyMap = mutableMapOf<Char, Ingredient>()

        infix fun Char.to(item: IItemProvider) {
            keyMap[this] = Ingredient.fromItems(item)
        }

        infix fun Char.to(tag: ITag<Item>) {
            keyMap[this] = Ingredient.fromTag(tag)
        }

        infix fun Char.to(ingredient: Ingredient) {
            keyMap[this] = ingredient
        }

        fun build(): Map<Char, Ingredient> = keyMap
    }

    class CriterionBuilder {
        private val criterionMap = mutableMapOf<String, ICriterionInstance>()

        infix fun String.to(criterionIn: ICriterionInstance) {
            criterionMap[this] = criterionIn
        }

        fun build(): Map<String, ICriterionInstance> = criterionMap
    }
}