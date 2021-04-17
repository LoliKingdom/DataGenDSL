package io.github.chaosunity.dgdsl.builder.utils

import net.minecraft.loot.RandomValueRange

interface ICommonLootEntryArgFunctions {
    fun Float.to(max: Float) =
        RandomValueRange(this, max)
}