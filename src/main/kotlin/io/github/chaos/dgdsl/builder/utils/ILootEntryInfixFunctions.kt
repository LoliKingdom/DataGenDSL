package io.github.chaos.dgdsl.builder.utils

import net.minecraft.loot.StandaloneLootEntry
import net.minecraft.loot.functions.ILootFunction

interface ILootEntryInfixFunctions {
    infix fun StandaloneLootEntry.Builder<*>.acceptFunctions(functions: List<ILootFunction.IBuilder>): StandaloneLootEntry.Builder<*> {
        functions.forEach(this::acceptFunction)

        return this
    }
}