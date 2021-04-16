package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.ItemLootEntryBuilder
import io.github.chaos.dgdsl.TableLootEntryBuilder
import io.github.chaos.dgdsl.TagLootEntryBuilder
import io.github.chaos.dgdsl.builder.AbstractBuilder
import io.github.chaos.dgdsl.builder.utils.IListInfixFunctions
import net.minecraft.item.Item
import net.minecraft.loot.*
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation

abstract class LootEntryBuilder :
    AbstractBuilder() {

    abstract fun condition(conditions: LootConditionBuilder.() -> Unit)

    abstract fun build(): LootEntry.Builder<*>

    open class StandaloneLootEntryBuilder(protected val builder: StandaloneLootEntry.Builder<*>) :
        LootEntryBuilder() {
        fun function(functions: LootFunctionBuilder.() -> Unit) =
            LootFunctionBuilder().apply(functions).build().forEach(builder::apply)

        override fun condition(conditions: LootConditionBuilder.() -> Unit) =
            LootConditionBuilder().apply(conditions).build().forEach(builder::`when`)

        override fun build(): LootEntry.Builder<*> =
            builder

        class ItemLootEntryBuilder(item: IItemProvider) :
            StandaloneLootEntryBuilder(ItemLootEntry.lootTableItem(item))

        class TagLootEntryBuilder(tag: ITag<Item>) :
            StandaloneLootEntryBuilder(TagLootEntry.expandTag(tag))

        class TableLootEntryBuilder(reference: ResourceLocation) :
            StandaloneLootEntryBuilder(TableLootEntry.lootTableReference(reference))
    }

    class AlternativesEntryBuilder : LootEntryBuilder(), IListInfixFunctions {
        private val conditions = mutableListOf<ILootCondition.IBuilder>()
        private val entries = mutableListOf<LootEntry.Builder<*>>()

        fun itemLoot(item: IItemProvider, builder: ItemLootEntryBuilder.() -> Unit = {}) =
            entries add ItemLootEntryBuilder(item).apply(builder).build()

        fun tagLoot(tag: ITag<Item>, builder: TagLootEntryBuilder.() -> Unit = {}) =
            entries add TagLootEntryBuilder(tag).apply(builder).build()

        fun tableLoot(reference: ResourceLocation, builder: TableLootEntryBuilder.() -> Unit = {}) =
            entries add TableLootEntryBuilder(reference).apply(builder).build()

        fun alternativesLoot(builder: AlternativesEntryBuilder.() -> Unit = {}) =
            entries add AlternativesEntryBuilder().apply(builder).build()

        override fun condition(conditions: LootConditionBuilder.() -> Unit) {
            this.conditions addAll LootConditionBuilder().apply(conditions).build()
        }

        override fun build(): LootEntry.Builder<*> =
            AlternativesLootEntry.alternatives(*entries.toTypedArray()).apply {
                conditions.forEach(::`when`)
            }
    }
}
