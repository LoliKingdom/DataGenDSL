package io.github.chaos.dgdsl.builder

import io.github.chaos.dgdsl.builder.loot.LootEntryBuilder
import net.minecraft.loot.ConstantRange
import net.minecraft.loot.LootPool
import net.minecraft.loot.StandaloneLootEntry

class LootPoolBuilder(private val builder: LootPool.Builder) : AbstractBuilder() {
    fun name(name: String) =
        builder.name(name)

    fun rolls(value: Int) =
        builder.setRolls(ConstantRange.exactly(value))

    fun rolls(value: () -> ConstantRange) =
        builder.setRolls(value.invoke())

    fun entry(entry: LootEntryBuilder.() -> StandaloneLootEntry.Builder<*>) =
        builder.add(LootEntryBuilder().run(entry))

    fun raw(): LootPool.Builder =
        builder

    fun build(): LootPool =
        builder.build()
}