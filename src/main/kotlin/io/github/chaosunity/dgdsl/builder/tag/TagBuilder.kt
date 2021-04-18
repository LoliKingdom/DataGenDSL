package io.github.chaosunity.dgdsl.builder.tag

import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.data.TagsProvider
import net.minecraft.tags.ITag
import net.minecraft.util.ResourceLocation

class TagBuilder<T>(private val builder: TagsProvider.Builder<T>) : AbstractBuilder() {
    infix fun String.to(path: String): ResourceLocation =
        ResourceLocation(this, path)

    fun element(vararg `in`: T) {
        `in`.forEach(builder::add)
    }

    fun tag(vararg tag: ITag.INamedTag<T>) {
        builder.addTags(*tag)
    }

    fun entry(entry: ITag.ITagEntry) {
        builder.add(entry)
    }

    fun optional(vararg `in`: ResourceLocation) {
        `in`.forEach(builder::addOptional)
    }

    fun optionalTag(vararg tag: ResourceLocation) {
        tag.forEach(builder::addOptionalTag)
    }
}