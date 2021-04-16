package io.github.chaos.dgdsl.examples

import net.minecraft.block.Blocks
import net.minecraft.data.DataGenerator

class LootTable(dataGenerator: DataGenerator) : BaseLoot(dataGenerator) {
    override fun addTables() {
        lootTables[Blocks.GRASS] = createStandardTables("grass", Blocks.GRASS_BLOCK)
    }
}