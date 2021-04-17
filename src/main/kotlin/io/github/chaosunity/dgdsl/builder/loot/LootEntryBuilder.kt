package io.github.chaosunity.dgdsl.builder.loot

import io.github.chaosunity.dgdsl.*
import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.item.Item
import net.minecraft.loot.*
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation

abstract class LootEntryBuilder : AbstractBuilder() {
    abstract fun condition(conditions: LootConditionBuilder.() -> Unit)

    abstract fun unwrap(): LootEntry.Builder<*>

    abstract fun build(): LootEntry.Builder<*>

    open class StandaloneLootEntryBuilder(protected val builder: StandaloneLootEntry.Builder<*>) :
        LootEntryBuilder() {
        fun function(functions: LootFunctionBuilder.() -> Unit) =
            LootFunctionBuilder().apply(functions).build().forEach(builder::apply)

        fun weight(weight: Int) {
            builder.setWeight(weight)
        }

        fun quality(quality: Int) {
            builder.setQuality(quality)
        }

        override fun condition(conditions: LootConditionBuilder.() -> Unit) =
            LootConditionBuilder().apply(conditions).build().forEach(builder::`when`)

        override fun unwrap(): LootEntry.Builder<*> =
            builder.unwrap()

        override fun build(): LootEntry.Builder<*> =
            builder

        class ItemLootEntryBuilder(item: IItemProvider) :
            StandaloneLootEntryBuilder(ItemLootEntry.lootTableItem(item))

        class TagLootEntryBuilder(tag: ITag<Item>) :
            StandaloneLootEntryBuilder(TagLootEntry.expandTag(tag))

        class TableLootEntryBuilder(reference: ResourceLocation) :
            StandaloneLootEntryBuilder(TableLootEntry.lootTableReference(reference))

        class DynamicLootEntryBuilder(reference: ResourceLocation) :
            StandaloneLootEntryBuilder(DynamicLootEntry.dynamicEntry(reference))

        class EmptyLootEntryBuilder :
            StandaloneLootEntryBuilder(EmptyLootEntry.emptyItem())
    }

    class AlternativesLootEntryBuilder : LootEntryBuilder(), ILootEntryBuilder {
        private val conditions = mutableListOf<ILootCondition.IBuilder>()
        private val entries = mutableListOf<LootEntry.Builder<*>>()

        private fun add(builder: LootEntry.Builder<*>) {
            entries += builder
        }

        override fun itemLoot(item: IItemProvider, builder: ItemLootEntryBuilder.() -> Unit) =
            add(ItemLootEntryBuilder(item).apply(builder).build())

        override fun tagLoot(tag: ITag<Item>, builder: TagLootEntryBuilder.() -> Unit) =
            add(TagLootEntryBuilder(tag).apply(builder).build())

        override fun tableLoot(reference: ResourceLocation, builder: TableLootEntryBuilder.() -> Unit) =
            add(TableLootEntryBuilder(reference).apply(builder).build())

        override fun alternativesLoot(builder: AlternativesLootEntryBuilder.() -> Unit) =
            add(AlternativesLootEntryBuilder().apply(builder).build())

        override fun dynamicLoot(reference: ResourceLocation, builder: DynamicLootEntryBuilder.() -> Unit) =
            add(DynamicLootEntryBuilder(reference).apply(builder).build())

        override fun emptyLoot(builder: EmptyLootEntryBuilder.() -> Unit) =
            add(EmptyLootEntryBuilder().apply(builder).build())

        override fun condition(conditions: LootConditionBuilder.() -> Unit) {
            this.conditions += LootConditionBuilder().apply(conditions).build()
        }

        override fun unwrap(): LootEntry.Builder<*> =
            AlternativesLootEntry.alternatives(*entries.toMutableList().toTypedArray()).apply {
                conditions.forEach(::`when`)
            }

        override fun build(): LootEntry.Builder<*> =
            AlternativesLootEntry.alternatives(*entries.toTypedArray()).apply {
                conditions.forEach(::`when`)
            }
    }
}
