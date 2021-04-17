package io.github.chaosunity.dgdsl.builder

import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.Effect

class TranslationBuilder : AbstractBuilder() {
    private val data = mutableMapOf<Any, String>()

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

    fun build(): Map<Any, String> =
        data
}