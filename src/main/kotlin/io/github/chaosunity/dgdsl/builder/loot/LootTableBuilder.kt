package io.github.chaosunity.dgdsl.builder.loot

import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.loot.LootParameterSet
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable

class LootTableBuilder(private val builder: LootTable.Builder) : AbstractBuilder() {
    /**
     *  Create a loot pool with exactly 1 roll in default. You can change roll to other numbers.
     */
    fun pool(roll: Int = 1, name: String = "", builder: LootPoolBuilder.() -> Unit) {
        this.builder.withPool(LootPoolBuilder(LootPool.lootPool(), roll).name(name).apply(builder).unwrap())
    }

    /**
     *  Create a loot pool with binomial range of rolls.
     */
    fun pool(n: Int, p: Float, name: String = "", builder: LootPoolBuilder.() -> Unit) {
        this.builder.withPool(LootPoolBuilder(LootPool.lootPool(), n, p).name(name).apply(builder).unwrap())
    }

    /**
     *  Create a loot pool with random range of rolls.
     */
    fun pool(min: Float, max: Float, name: String = "", builder: LootPoolBuilder.() -> Unit) {
        this.builder.withPool(LootPoolBuilder(LootPool.lootPool(), min, max).name(name).apply(builder).unwrap())
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