package io.github.chaosunity.dgdsl.builder.tag

import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.block.Block
import net.minecraft.data.BlockTagsProvider
import net.minecraft.tags.BlockTags
import net.minecraft.util.ResourceLocation

class BlockTagBuilder(private val provider: BlockTagsProvider) : AbstractBuilder(), ITagBuilder<Block> {
    override fun ResourceLocation.bind(builder: TagBuilder<Block>.() -> Unit) {
        val providerBuilder = provider.tag(BlockTags.bind(toString()))
        TagBuilder(providerBuilder).apply(builder)
    }
}