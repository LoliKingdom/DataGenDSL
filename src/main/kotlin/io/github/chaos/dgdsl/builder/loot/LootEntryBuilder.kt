package io.github.chaos.dgdsl.builder.loot

import net.minecraft.loot.LootEntry
import net.minecraft.loot.StandaloneLootEntry

open class LootEntryBuilder<T: LootEntryBuilder<T>>(protected val builder: StandaloneLootEntry.Builder<*>) {
}