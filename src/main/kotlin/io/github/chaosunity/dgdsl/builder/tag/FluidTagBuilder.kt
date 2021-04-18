package io.github.chaosunity.dgdsl.builder.tag

import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.data.FluidTagsProvider
import net.minecraft.fluid.Fluid
import net.minecraft.tags.FluidTags
import net.minecraft.util.ResourceLocation

class FluidTagBuilder(private val provider: FluidTagsProvider) : AbstractBuilder(), ITagBuilder<Fluid> {
    override fun ResourceLocation.bind(builder: TagBuilder<Fluid>.() -> Unit) {
        val providerBuilder = provider.tag(FluidTags.bind(toString()))
        TagBuilder(providerBuilder).apply(builder)
    }
}