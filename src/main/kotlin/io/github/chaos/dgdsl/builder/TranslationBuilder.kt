package io.github.chaos.dgdsl.builder

import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.Effect
import java.util.*

class TranslationBuilder {
    private val data = TreeMap<Any, String>()

    infix fun Block.to(name: String) {
        data[this] = name
    }

    infix fun Item.to(name: String) {
        data[this] = name
    }

    infix fun ItemStack.to(name: String) {
        data[this] = name
    }

    infix fun Enchantment.to(name: String) {
        data[this] = name
    }

    infix fun Effect.to(name: String) {
        data[this] = name
    }

    infix fun EntityType<*>.to(name: String) {
        data[this] = name
    }

    infix fun String.to(name: String) {
        data[this] = name
    }

    fun build(): TreeMap<Any, String> =
        data

    operator fun TreeMap<*, *>.set(key: Any, name: String) {
        if (this.let { this[key] = name; this[key] != null })
            throw IllegalStateException("Duplicate translation key $key")
    }
}