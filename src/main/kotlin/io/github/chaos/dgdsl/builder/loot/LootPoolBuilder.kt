package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.*
import io.github.chaos.dgdsl.builder.AbstractBuilder
import net.minecraft.item.Item
import net.minecraft.loot.*
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation

class LootPoolBuilder private constructor(private val builder: LootPool.Builder, rolls: IRandomRange) :
    AbstractBuilder(), ILootEntryBuilder {
    /**
     *  Build a loot pool with exactly n rolls.
     */
    constructor(builder: LootPool.Builder, rolls: Int) : this(builder, ConstantRange.exactly(rolls))

    /**
     *  Build a loot pool with binomial range.
     */
    constructor(builder: LootPool.Builder, n: Int, p: Float) : this(builder, BinomialRange.binomial(n, p))

    /**
     *  Build a loot pool with rand value range.
     */
    constructor(builder: LootPool.Builder, min: Float, max: Float) : this(builder, RandomValueRange.between(min, max))

    init {
        builder.setRolls(rolls)
    }

    fun name(name: String): LootPoolBuilder {
        builder.name(name)
        return this
    }

    fun bonusRolls(min: Float, max: Float) {
        builder.bonusRolls(min, max)
    }

    fun function(builder: LootFunctionBuilder.() -> Unit) =
        LootFunctionBuilder().apply(builder).build().forEach(this.builder::apply)

    fun condition(builder: LootConditionBuilder.() -> Unit) =
        LootConditionBuilder().apply(builder).build().forEach(this.builder::`when`)

    /**
     *  Loot Entries
     */

    private fun add(builder: LootEntry.Builder<*>) {
        this.builder.add(builder)
    }

    override fun itemLoot(item: IItemProvider, builder: ItemLootEntryBuilder.() -> Unit) =
        add(ItemLootEntryBuilder(item).apply(builder).build())

    override fun tagLoot(tag: ITag<Item>, builder: TagLootEntryBuilder.() -> Unit) =
        add(TagLootEntryBuilder(tag).apply(builder).build())

    override fun tableLoot(reference: ResourceLocation, builder: TableLootEntryBuilder.() -> Unit) =
        add(TableLootEntryBuilder(reference).apply(builder).build())

    override fun alternativesLoot(builder: AlternativesEntryBuilder.() -> Unit) =
        add(AlternativesEntryBuilder().apply(builder).build())

    override fun dynamicLoot(reference: ResourceLocation, builder: DynamicLootEntryBuilder.() -> Unit) =
        add(DynamicLootEntryBuilder(reference).apply(builder).build())

    override fun emptyLoot(builder: EmptyLootEntryBuilder.() -> Unit) =
        add(EmptyLootEntryBuilder().apply(builder).build())

    fun unwrap() =
        builder.unwrap()

    fun build() =
        builder.build()
}