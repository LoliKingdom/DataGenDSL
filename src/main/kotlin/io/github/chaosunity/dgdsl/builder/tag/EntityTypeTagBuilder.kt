package io.github.chaosunity.dgdsl.builder.tag

import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.data.EntityTypeTagsProvider
import net.minecraft.entity.EntityType
import net.minecraft.tags.EntityTypeTags
import net.minecraft.util.ResourceLocation

class EntityTypeTagBuilder(private val provider: EntityTypeTagsProvider) : AbstractBuilder(),
    ITagBuilder<EntityType<*>> {
    override fun ResourceLocation.bind(builder: TagBuilder<EntityType<*>>.() -> Unit) {
        val providerBuilder = provider.tag(EntityTypeTags.bind(toString()))
        TagBuilder(providerBuilder).apply(builder)
    }
}