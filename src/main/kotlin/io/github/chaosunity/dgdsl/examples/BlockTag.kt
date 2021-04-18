package io.github.chaosunity.dgdsl.examples

import io.github.chaosunity.dgdsl.DataGeneratorDSL
import io.github.chaosunity.dgdsl.tag
import net.minecraft.block.Blocks
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.tags.BlockTags
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper

class BlockTag(gen: DataGenerator, existingFileHelper: ExistingFileHelper) :
    BlockTagsProvider(gen, DataGeneratorDSL.MODID, existingFileHelper) {
    override fun addTags() {
        tag {
            ("minecraft" to "test2").bind {
                element(Blocks.DIAMOND_BLOCK)
                tag(BlockTags.STONE_BRICKS, Tags.Blocks.COBBLESTONE)
                optional("chisel" to "marble/raw")
                optionalTag("forge" to "storage_blocks/ruby")
            }
        }
    }
}