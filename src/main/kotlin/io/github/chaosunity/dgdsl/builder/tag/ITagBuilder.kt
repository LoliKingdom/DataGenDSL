package io.github.chaosunity.dgdsl.builder.tag

import net.minecraft.util.ResourceLocation

interface ITagBuilder<T> {
    infix fun String.to(path: String): ResourceLocation =
        ResourceLocation(this, path)

    fun ResourceLocation.bind(builder: TagBuilder<T>.() -> Unit)
}