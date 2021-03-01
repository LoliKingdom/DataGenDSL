package io.github.chaos.dgdsl

import net.minecraft.util.ResourceLocation

internal fun makeID(path: String): ResourceLocation =
    ResourceLocation(DataGeneratorDSL.MODID, path)