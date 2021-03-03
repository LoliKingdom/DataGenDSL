package io.github.chaos.dgdsl.builder.loot

import net.minecraft.loot.StandaloneLootEntry

class ItemLootEntryBuilder(builder: StandaloneLootEntry.Builder<*>) : LootEntryBuilder<ItemLootEntryBuilder>(builder) {
    fun function(function: LootFunctionBuilder.() -> Unit) {
        val functions = LootFunctionBuilder().apply(function).build()
        functions.forEach(builder::acceptFunction)
    }
}