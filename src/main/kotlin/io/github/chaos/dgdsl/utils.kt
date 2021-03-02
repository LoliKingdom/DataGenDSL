package io.github.chaos.dgdsl

import net.minecraft.util.ResourceLocation

internal fun makeID(path: String): ResourceLocation =
    ResourceLocation(DataGeneratorDSL.MODID, path)

internal fun pairToID(pair: Pair<String, String>): ResourceLocation =
    ResourceLocation(pair.first, pair.second)