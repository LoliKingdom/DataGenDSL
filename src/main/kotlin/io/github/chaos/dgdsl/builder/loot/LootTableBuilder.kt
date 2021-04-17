package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.builder.AbstractBuilder
import net.minecraft.loot.LootParameterSet
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable

class LootTableBuilder(private val builder: LootTable.Builder) : AbstractBuilder() {
    fun pool(roll: Int = 1, name: String = "", builder: LootPoolBuilder.() -> Unit) {
        this.builder.withPool(LootPoolBuilder(LootPool.lootPool(), roll).name(name).apply(builder).unwrap())
    }

    fun parameterSet(parameterSet: LootParameterSet) {
        this.builder.setParamSet(parameterSet)
    }

    fun parameterSet(builder: LootParameterSetBuilder.() -> Unit) {
        this.builder.setParamSet(LootParameterSetBuilder(LootParameterSet.Builder()).apply(builder).build().build())
    }

    fun function(builder: LootFunctionBuilder.() -> Unit) {
        LootFunctionBuilder().apply(builder).build().forEach(this.builder::apply)
    }

    fun unwrap() =
        builder.unwrap()

    fun build() =
        builder
}