package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.builder.utils.IListInfixFunctions
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.loot.IRandomRange
import net.minecraft.loot.IntClamper
import net.minecraft.loot.LootContext
import net.minecraft.loot.LootFunction
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.loot.functions.*
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.storage.MapDecoration
import kotlin.properties.Delegates

class LootFunctionBuilder : IListInfixFunctions {
    private val functions = mutableListOf<ILootFunction>()

    /**
     *  Apply Bonus
     */

    fun binomialWithBonusCount(enchantment: Enchantment, extra: Int, probability: Float) =
        functions add ApplyBonus.binomialWithBonusCount(enchantment, probability, extra).build()

    fun oreDrops(enchantment: Enchantment) =
        functions add ApplyBonus.oreDrops(enchantment).build()

    fun uniformBonusCount(enchantment: Enchantment, bonusMultiplier: Int = 1) =
        functions add ApplyBonus.uniformBonusCount(enchantment, bonusMultiplier).build()

    /**
     *  Copy BlockState
     */

    fun copyBlockState(block: Block, builder: CopyBlockState.Builder.() -> Unit = {}) =
        functions add CopyBlockState.func_227545_a_(block).apply(builder).build()

    /**
     *  Copy Name
     */

    fun copyName(source: CopyName.Source, builder: LootFunction.Builder<*>.() -> Unit = {}) =
        functions add CopyName.builder(source).apply(builder).build()

    /**
     *  Copy NBT
     */

    fun copyNbt(source: CopyNbt.Source, builder: CopyNbt.Builder.() -> Unit = {}) =
        functions add CopyNbt.builder(source).apply(builder).build()

    /**
     *  Enchant Randomly
     */

    fun enchantRandomly(collector: EnchantmentCollector.() -> Unit, builder: LootFunction.Builder<*>.() -> Unit = {}) {
        functions add EnchantmentCollector().apply(collector).collect().apply(builder).build()
    }

    inner class EnchantmentCollector : IListInfixFunctions {
        private val builder = EnchantRandomly.Builder()
        private val enchantments = mutableListOf<Enchantment>()

        fun enchantment(enchantment: () -> Enchantment) =
            enchantments add enchantment.invoke()

        fun function(function: LootFunction.Builder<*>.() -> Unit) =
            builder.apply(function)

        fun collect(): EnchantRandomly.Builder {
            enchantments.forEach(builder::func_237424_a_)

            return builder
        }
    }

    /**
     *  Enchant With Levels
     */

    fun enchantWithLevels(
        randomRange: IRandomRange,
        isTreasure: Boolean = false,
        enchantWithLevels: EnchantWithLevels.Builder.() -> Unit = {}
    ) = functions add EnchantWithLevels.func_215895_a(randomRange)
        .also { if (isTreasure) it.func_216059_e() }
        .apply(enchantWithLevels)
        .build()

    /**
     *  Exploration Map
     */

    fun explorationMap(explorationMap: ExplorationMapCollector.() -> Unit) =
        functions add ExplorationMapCollector().apply(explorationMap).collect().build()

    inner class ExplorationMapCollector {
        private val builder: ExplorationMap.Builder = ExplorationMap.func_215903_b()

        fun destination(destination: () -> Structure<*>) =
            builder.func_237427_a_(destination.invoke())

        fun decoration(decoration: MapDecoration.Type) =
            builder.func_216064_a(decoration)

        fun zoom(zoom: Byte) =
            builder.func_216062_a(zoom)

        fun zoom(zoom: () -> Byte) =
            zoom(zoom.invoke())

        fun skipExistingChunk(skip: Boolean) =
            builder.func_216063_a(skip)

        fun skipExistingChunk(skip: () -> Boolean) =
            skipExistingChunk(skip.invoke())

        fun function(function: LootFunction.Builder<*>.() -> Unit) =
            builder.apply(function)

        fun collect() =
            builder
    }

    /**
     *  Explosion Decay
     */

    fun explosionDecay(decay: LootFunction.Builder<*>.() -> Unit = {}) =
        functions add ExplosionDecay.builder().apply(decay).build()

    /**
     *  Fill Player Head
     */

    /**
     * Not sure how to implement this into dsl form.
     * Investigate required.
     *
     * @author ChAoS
     */
    fun fillPlayerHead(conditions: Array<ILootCondition>, target: LootContext.EntityTarget) =
        functions add FillPlayerHead(conditions, target)

    /**
     *  Limit Count
     */

    fun limitCount(clamper: IntClamper, builder: LootFunction.Builder<*>.() -> Unit = {}) =
        functions add LimitCount.func_215911_a(clamper).apply(builder).build()

    fun build(): List<ILootFunction> =
        functions
}