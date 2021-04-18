package io.github.chaosunity.dgdsl.builder.tag

import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.data.ItemTagsProvider
import net.minecraft.item.Item
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation

class ItemTagBuilder (private val provider: ItemTagsProvider) : AbstractBuilder(), ITagBuilder<Item> {
    override fun ResourceLocation.bind(builder: TagBuilder<Item>.() -> Unit) {
        val providerBuilder = provider.tag(ItemTags.bind(toString()))
        TagBuilder(providerBuilder).apply(builder)
    }
}