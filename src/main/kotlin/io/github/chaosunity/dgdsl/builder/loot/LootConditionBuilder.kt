package io.github.chaosunity.dgdsl.builder.loot

import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.advancements.criterion.*
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.loot.LootContext
import net.minecraft.loot.conditions.*
import net.minecraft.util.math.BlockPos

class LootConditionBuilder : AbstractBuilder() {
    private val conditions = mutableListOf<ILootCondition.IBuilder>()

    private fun add(condition: ILootCondition.IBuilder, invert: Boolean) {
        this.conditions += if (invert) {
            Inverted.invert(condition)
        } else {
            condition
        }
    }

    /**
     *  Alternative
     */

    fun alternative(builder: LootConditionBuilder.() -> Unit, invert: Boolean = false) =
        add(Alternative.alternative(*LootConditionBuilder().apply(builder).build().toTypedArray()), invert)

    /**
     *  BlockState Property
     */

    fun blockStateProperty(block: Block, invert: Boolean = false) =
        add(BlockStateProperty.hasBlockStateProperties(block), invert)

    fun blockStateProperty(block: Block, property: StatePropertiesPredicate.Builder, invert: Boolean = false) =
        add(BlockStateProperty.hasBlockStateProperties(block).setProperties(property), invert)

    /**
     *  Damage Source Properties
     */

    fun damageSourceProperty(builder: DamageSourcePredicate.Builder, invert: Boolean = false) =
        add(DamageSourceProperties.hasDamageSource(builder), invert)

    /**
     *  Entity Has Property
     */

    fun entityPresent(target: LootContext.EntityTarget, invert: Boolean = false) =
        add(EntityHasProperty.entityPresent(target), invert)

    fun entityHasProperty(target: LootContext.EntityTarget, predicate: EntityPredicate, invert: Boolean = false) =
        add(EntityHasProperty.hasProperties(target, predicate), invert)

    fun entityHasProperty(target: LootContext.EntityTarget, builder: EntityPredicate.Builder, invert: Boolean = false) =
        entityHasProperty(target, builder.build())

    /**
     *  Entity Has Score
     */

    /**
     *  Inverted
     *
     *  NOTE: INVERTED IS ALREADY IMPLEMENTED IN EVERY POSSIBLE CONDITION FUNCTIONS.
     */

    /**
     *  Killed By Player
     */

    fun killedByPlayer(invert: Boolean = false) =
        add(KilledByPlayer.killedByPlayer(), invert)

    /**
     *  Location Check
     */

    fun checkLocation(builder: LocationPredicate.Builder, position: BlockPos = BlockPos.ZERO, invert: Boolean = false) =
        add(LocationCheck.checkLocation(builder, position), invert)

    /**
     *  Match Tool
     */

    fun toolMatches(builder: ItemPredicate.Builder, invert: Boolean = false) =
        add(MatchTool.toolMatches(builder), invert)

    /**
     *  Random Chance
     */

    fun randomChance(percentage: Float, invert: Boolean = false) =
        add(RandomChance.randomChance(percentage), invert)

    /**
     *  Random Chance With Looting
     */

    fun randomChanceAndLootingBoost(percentage: Float, lootingBoost: Float, invert: Boolean = false) =
        add(RandomChanceWithLooting.randomChanceAndLootingBoost(percentage, lootingBoost), invert)

    /**
     *  Reference
     */

    /**
     *  Survives Explosion
     */

    fun surviveExplosion(invert: Boolean = false) =
        add(SurvivesExplosion.survivesExplosion(), invert)

    /**
     *  Table Bonus
     */

    fun bonusLevelFlatChance(enchantment: Enchantment, vararg values: Float, invert: Boolean = false) =
        add(TableBonus.bonusLevelFlatChance(enchantment, *values), invert)

    /**
     *  Time Check
     */

    /**
     *  Weather Check
     */

    fun build(): List<ILootCondition.IBuilder> =
        conditions
}