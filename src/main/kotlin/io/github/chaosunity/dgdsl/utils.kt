package io.github.chaosunity.dgdsl

import io.github.chaosunity.dgdsl.builder.loot.LootEntryBuilder
import net.minecraft.util.ResourceLocation

typealias ItemLootEntryBuilder = LootEntryBuilder.StandaloneLootEntryBuilder.ItemLootEntryBuilder
typealias TagLootEntryBuilder = LootEntryBuilder.StandaloneLootEntryBuilder.TagLootEntryBuilder
typealias TableLootEntryBuilder = LootEntryBuilder.StandaloneLootEntryBuilder.TableLootEntryBuilder
typealias AlternativesEntryBuilder = LootEntryBuilder.AlternativesLootEntryBuilder
typealias DynamicLootEntryBuilder = LootEntryBuilder.StandaloneLootEntryBuilder.DynamicLootEntryBuilder
typealias EmptyLootEntryBuilder = LootEntryBuilder.StandaloneLootEntryBuilder.EmptyLootEntryBuilder

internal fun makeID(path: String): ResourceLocation =
    ResourceLocation(DataGeneratorDSL.MODID, path)

internal fun pairToID(pair: Pair<String, String>): ResourceLocation =
    ResourceLocation(pair.first, pair.second)