package io.github.chaosunity.dgdsl.examples

import io.github.chaosunity.dgdsl.DataGeneratorDSL
import io.github.chaosunity.dgdsl.tag
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.ItemTagsProvider
import net.minecraft.item.Items
import net.minecraft.tags.ItemTags
import net.minecraftforge.common.data.ExistingFileHelper

class ItemTag(gen: DataGenerator, provider: BlockTagsProvider, existingFileHelper: ExistingFileHelper) : ItemTagsProvider(gen, provider, DataGeneratorDSL.MODID, existingFileHelper) {
    override fun addTags() {
        tag {
            ("minecraft" to "test").bind {
                element(Items.DIAMOND_BLOCK)
                tag(ItemTags.GOLD_ORES, ItemTags.COALS)
                optional("chisel" to "marble/raw")
                optionalTag("forge" to "storage_blocks/ruby")
            }
        }
    }
}