package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.*
import net.minecraft.item.Item
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation

interface ILootEntryBuilder {
    fun itemLoot(item: IItemProvider, builder: ItemLootEntryBuilder.() -> Unit = {})

    fun tagLoot(tag: ITag<Item>, builder: TagLootEntryBuilder.() -> Unit = {})

    fun tableLoot(reference: ResourceLocation, builder: TableLootEntryBuilder.() -> Unit = {})

    fun alternativesLoot(builder: AlternativesEntryBuilder.() -> Unit = {})

    fun dynamicLoot(reference: ResourceLocation, builder: DynamicLootEntryBuilder.() -> Unit = {})

    fun emptyLoot(builder: EmptyLootEntryBuilder.() -> Unit = {})
}