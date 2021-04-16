package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.builder.AbstractBuilder
import net.minecraft.loot.ItemLootEntry
import net.minecraft.loot.StandaloneLootEntry
import net.minecraft.util.IItemProvider

open class LootEntryBuilder : AbstractBuilder() {
    fun itemLoot(item: IItemProvider, builder: ItemLootEntryBuilder.() -> Unit) =
        ItemLootEntryBuilder(item).apply(builder).build()

    class ItemLootEntryBuilder(item: IItemProvider) : LootEntryBuilder() {
        private val builder = ItemLootEntry.lootTableItem(item)

        fun function(function: LootFunctionBuilder.() -> Unit) =
            LootFunctionBuilder().apply(function).build().forEach(builder::apply)

        fun condition(condition: LootConditionBuilder.() -> Unit) =
            LootConditionBuilder().apply(condition).build().forEach(builder::`when`)

        fun build(): StandaloneLootEntry.Builder<*> =
            builder
    }
}
