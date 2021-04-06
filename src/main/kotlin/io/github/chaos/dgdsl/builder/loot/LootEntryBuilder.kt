package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.builder.AbstractBuilder
import io.github.chaos.dgdsl.builder.utils.IListInfixFunctions
import io.github.chaos.dgdsl.builder.utils.ILootEntryInfixFunctions
import net.minecraft.loot.ItemLootEntry
import net.minecraft.loot.StandaloneLootEntry
import net.minecraft.loot.functions.ILootFunction
import net.minecraft.util.IItemProvider

open class LootEntryBuilder : AbstractBuilder() {
    fun itemLoot(item: IItemProvider, builder: ItemLootEntryBuilder.() -> Unit) =
        ItemLootEntryBuilder(item).apply(builder).build()

    class ItemLootEntryBuilder(item: IItemProvider) : LootEntryBuilder(), IListInfixFunctions,
        ILootEntryInfixFunctions {
        private val builder = ItemLootEntry.lootTableItem(item)
        private val functions = mutableListOf<ILootFunction.IBuilder>()


        fun function(function: LootFunctionBuilder.() -> Unit) =
            functions addAll LootFunctionBuilder().apply(function).build()

        fun build(): StandaloneLootEntry.Builder<*> =
            builder.acceptFunctions(functions)
    }
}
