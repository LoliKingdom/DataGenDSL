package io.github.chaos.dgdsl

import net.minecraft.data.IFinishedRecipe
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.item.Item
import net.minecraft.item.crafting.Ingredient
import net.minecraft.tags.ITag
import java.util.function.Consumer

class RecipeBuilder(private val builder: ShapedRecipeBuilder) {
    fun pattern(patternLines: () -> String) {
        val patterns = patternLines.invoke().replace("\\s".toRegex(), "").chunked(3)

        for (pattern in patterns)
            builder.patternLine(pattern)
    }

    fun itemKey(keys: () -> Pair<Char, Item>) {
        println(keys)
        val (character, tag) = keys.invoke()
        builder.key(character, tag)
    }

    fun tagKey(keys: () -> Pair<Char, ITag<Item>>) {
        val (character, tag) = keys.invoke()
        builder.key(character, tag)
    }

    fun group(group: () -> String) {
        builder.setGroup(group.invoke())
    }

    fun build(consumer: Consumer<IFinishedRecipe>) =
        builder.build(consumer)

    class KeyBuilder {
        private val keyMap: MutableMap<Char, Ingredient> = mutableMapOf()

    }
}