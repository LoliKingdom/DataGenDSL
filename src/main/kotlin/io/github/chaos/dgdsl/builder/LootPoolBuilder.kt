package io.github.chaos.dgdsl.builder

import io.github.chaos.dgdsl.AlternativesEntryBuilder
import io.github.chaos.dgdsl.ItemLootEntryBuilder
import io.github.chaos.dgdsl.TableLootEntryBuilder
import io.github.chaos.dgdsl.TagLootEntryBuilder
import io.github.chaos.dgdsl.builder.loot.LootConditionBuilder
import net.minecraft.item.Item
import net.minecraft.loot.ConstantRange
import net.minecraft.loot.LootPool
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation

class LootPoolBuilder(private val builder: LootPool.Builder) : AbstractBuilder() {
    fun name(name: String) =
        builder.name(name)

    fun rolls(value: Int) =
        builder.setRolls(ConstantRange.exactly(value))

    fun rolls(value: () -> ConstantRange) =
        builder.setRolls(value.invoke())

    fun condition(condition: LootConditionBuilder.() -> Unit) =
        LootConditionBuilder().apply(condition).build().forEach(builder::`when`)

    /**
     *  Loot Entries
     */

    fun itemLoot(item: IItemProvider, builder: ItemLootEntryBuilder.() -> Unit = {}) =
        this.builder.add(ItemLootEntryBuilder(item).apply(builder).build())

    fun tagLoot(tag: ITag<Item>, builder: TagLootEntryBuilder.() -> Unit = {}) =
        this.builder.add(TagLootEntryBuilder(tag).apply(builder).build())

    fun tableLoot(reference: ResourceLocation, builder: TableLootEntryBuilder.() -> Unit = {}) =
        this.builder.add(TableLootEntryBuilder(reference).apply(builder).build())

    fun alternativesLoot(builder: AlternativesEntryBuilder.() -> Unit = {}) =
        this.builder.add(AlternativesEntryBuilder().apply(builder).build())

    fun raw(): LootPool.Builder =
        builder

    fun build(): LootPool =
        builder.build()
}