package io.github.chaosunity.dgdsl.builder.loot

import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.loot.LootParameter
import net.minecraft.loot.LootParameterSet

class LootParameterSetBuilder(private val builder: LootParameterSet.Builder) : AbstractBuilder() {
    fun optional(vararg parameters: LootParameter<*>) {
        parameters.forEach(builder::optional)
    }

    fun required(vararg parameters: LootParameter<*>) {
        parameters.forEach(builder::required)
    }

    fun build() =
        builder
}