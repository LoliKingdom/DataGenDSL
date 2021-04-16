package io.github.chaos.dgdsl.builder.loot

import io.github.chaos.dgdsl.builder.AbstractBuilder
import io.github.chaos.dgdsl.builder.BuilderEntryMarker
import io.github.chaos.dgdsl.builder.utils.ICommonLootEntryArgFunctions
import io.github.chaos.dgdsl.builder.utils.IListInfixFunctions
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.loot.*
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.loot.functions.*
import net.minecraft.nbt.CompoundNBT
import net.minecraft.potion.Effect
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.storage.MapDecoration

class LootFunctionBuilder : AbstractBuilder(), IListInfixFunctions {
    private val functions = mutableListOf<ILootFunction.IBuilder>()

    open class LootFunctionCollector : AbstractBuilder() {
        private val conditions = mutableListOf<ILootCondition>()

        fun condition() {

        }
    }

    /**
     *  Apply Bonus
     */

    fun binomialWithBonusCount(enchantment: Enchantment, extra: Int, probability: Float) =
        functions add ApplyBonus.addBonusBinomialDistributionCount(enchantment, probability, extra)

    fun oreDrops(enchantment: Enchantment) =
        functions add ApplyBonus.addOreBonusCount(enchantment)

    fun uniformBonusCount(enchantment: Enchantment, bonusMultiplier: Int = 1) =
        functions add ApplyBonus.addUniformBonusCount(enchantment, bonusMultiplier)

    /**
     *  Copy BlockState
     */

    fun copyBlockState(block: Block, builder: CopyBlockState.Builder.() -> Unit = {}) =
        functions add CopyBlockState.copyState(block).apply(builder)

    /**
     *  Copy Name
     */

    fun copyName(source: CopyName.Source, builder: LootFunction.Builder<*>.() -> Unit = {}) =
        functions add CopyName.copyName(source).apply(builder)

    /**
     *  Copy NBT
     */

    fun copyNbt(source: CopyNbt.Source, builder: CopyNbt.Builder.() -> Unit = {}) =
        functions.add(CopyNbt.copyData(source).apply(builder))

    /**
     *  Enchant Randomly
     */

    fun enchantRandomly(collector: EnchantmentCollector.() -> Unit, builder: LootFunction.Builder<*>.() -> Unit = {}) {
        functions add EnchantmentCollector().apply(collector).collect().apply(builder)
    }

    inner class EnchantmentCollector : IListInfixFunctions {
        private val builder = EnchantRandomly.Builder()
        private val enchantments = mutableListOf<Enchantment>()

        fun enchantment(enchantment: () -> Enchantment) =
            enchantments add enchantment.invoke()

        fun function(function: LootFunction.Builder<*>.() -> Unit) =
            builder.apply(function)

        fun collect(): EnchantRandomly.Builder {
            enchantments.forEach(builder::withEnchantment)

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
    ) = functions add EnchantWithLevels.enchantWithLevels(randomRange)
        .also { if (isTreasure) it.allowTreasure() }
        .apply(enchantWithLevels)

    /**
     *  Exploration Map
     */

    fun explorationMap(explorationMap: ExplorationMapCollector.() -> Unit) =
        functions add ExplorationMapCollector().apply(explorationMap).collect()

    inner class ExplorationMapCollector {
        private val builder: ExplorationMap.Builder = ExplorationMap.makeExplorationMap()

        fun destination(destination: () -> Structure<*>) =
            builder.setDestination(destination.invoke())

        fun decoration(decoration: MapDecoration.Type) =
            builder.setMapDecoration(decoration)

        fun zoom(zoom: Byte) =
            builder.setZoom(zoom)

        fun zoom(zoom: () -> Byte) =
            zoom(zoom.invoke())

        fun skipExistingChunk(skip: Boolean) =
            builder.setSkipKnownStructures(skip)

        fun skipExistingChunk(skip: () -> Boolean) =
            skipExistingChunk(skip.invoke())

        fun collect() =
            builder
    }

    /**
     *  Explosion Decay
     */

    fun explosionDecay(decay: LootFunction.Builder<*>.() -> Unit = {}) =
        functions add ExplosionDecay.explosionDecay().apply(decay)

    /**
     *  Fill Player Head
     *
     *  Not sure how to implement this into dsl form.
     *  Investigate required.
     *
     *  @author ChAoS
     */

    /**
     *  Limit Count
     */

    fun limitCount(min: Int? = null, max: Int? = null, builder: LootFunction.Builder<*>.() -> Unit = {}) {
        val clamper = if (min != null && max == null) {
            IntClamper.lowerBound(min)
        } else if (min == null && max != null) {
            IntClamper.upperBound(max)
        } else if (min != null && max != null) {
            IntClamper.clamp(min, max)
        } else throw IllegalArgumentException("IntClamper requires at least on value.")

        functions add LimitCount.limitCount(clamper).apply(builder)
    }

    /**
     *  Looting Enchant Bonus
     */

    fun lootEnchantBonus(min: Float, max: Float, limit: Int = 0, builder: LootFunction.Builder<*>.() -> Unit = {}) =
        functions add LootingEnchantBonus.lootingMultiplier(RandomValueRange(min, max)).setLimit(limit).apply(builder)

    /**
     *  Set Attributes is not open to modders in default
     */

    /**
     *  Set Contents
     */

    fun content(lootEntries: ContentCollector.() -> Unit) =
        functions add ContentCollector().apply(lootEntries).collect()

    inner class ContentCollector : LootFunctionCollector(), IListInfixFunctions {
        private val builder = SetContents.setContents()
        private val lootEntries = mutableListOf<LootEntry.Builder<*>>()

        fun content(item: IItemProvider, lootEntry: LootEntryBuilder.ItemLootEntryBuilder.() -> Unit) =
            lootEntries add LootEntryBuilder.ItemLootEntryBuilder(item).apply(lootEntry).build()

        fun collect() =
            builder.apply { lootEntries.forEach(::withEntry) }
    }

    /**
     *  Set Damage
     */

    fun damage(min: Float, max: Float, builder: LootFunction.Builder<*>.() -> Unit) =
        functions add SetDamage.setDamage(RandomValueRange(min, max)).apply(builder)

    /**
     *  Set Loot Table is not open to modders in default
     */

    /**
     *  Set Lore
     *
     *  Not sure how to implement this into dsl form.
     *  Investigate required.
     *
     *  @author ChAoS
     */

    /**
     *  Set Name
     *
     *  Not sure how to implement this into dsl form.
     *  Investigate required.
     *
     *  @author ChAoS
     */

    /**
     *  Set NBT
     */

    fun nbt(nbt: CompoundNBT, builder: LootFunction.Builder<*>.() -> Unit) =
        functions add SetNBT.setTag(nbt).apply(builder)

    /**
     *  Set Stew Effect
     */

    fun stewEffect(builder: StewEffectCollector.() -> Unit) =
        functions add StewEffectCollector().apply(builder).collect()

    inner class StewEffectCollector : LootFunctionCollector(), ICommonLootEntryArgFunctions {
        private val builder = SetStewEffect.stewEffect()
        private val effects = mutableMapOf<Effect, RandomValueRange>()

        operator fun Pair<Effect, RandomValueRange>.unaryPlus() =
            mutableMapOf(this)

        operator fun Pair<Effect, RandomValueRange>.plus(another: Pair<Effect, RandomValueRange>) =
            mutableMapOf(this, another)

        fun effect(effects: () -> Map<Effect, RandomValueRange>) =
            effects.invoke().forEach(this.effects::put)

        fun collect() =
            builder.apply { effects.forEach(::withEffect) }
    }

    /**
     *  Smelt
     */

    fun smelt(builder: LootFunction.Builder<*>.() -> Unit) =
        functions add Smelt.smelted().apply(builder)

    fun build(): List<ILootFunction.IBuilder> =
        functions
}