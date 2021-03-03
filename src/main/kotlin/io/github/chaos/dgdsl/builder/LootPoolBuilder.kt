package io.github.chaos.dgdsl.builder

import net.minecraft.loot.ConstantRange
import net.minecraft.loot.LootPool

class LootPoolBuilder(private val builder: LootPool.Builder) {
    fun name(name: String) =
        builder.name(name)

    fun rolls(value: Int) =
        builder.rolls(ConstantRange.of(value))

    fun rolls(value: () -> ConstantRange) =
        builder.rolls(value.invoke())

    fun entry(entry: Entry) =
        builder.addEntry(entry)

    fun raw(): LootPool.Builder =
        builder

    fun build(): LootPool =
        builder.build()
}