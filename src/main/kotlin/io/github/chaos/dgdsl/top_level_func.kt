package io.github.chaos.dgdsl

import io.github.chaos.dgdsl.builder.*
import io.github.chaos.dgdsl.builder.nbt.CompoundBuilder
import net.minecraft.block.Block
import net.minecraft.data.ItemModelProvider
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootPool
import net.minecraft.nbt.CompoundNBT
import net.minecraft.potion.Effect
import net.minecraft.util.IItemProvider
import net.minecraftforge.common.data.LanguageProvider

fun recipe(result: IItemProvider, resultCount: Int = 1, builder: RecipeBuilder.() -> Unit): RecipeBuilder =
    RecipeBuilder(ShapedRecipeBuilder(result, resultCount), result).apply(builder)

fun conditionalRecipe(builder: ConditionalRecipeBuilder.() -> Unit): ConditionalRecipeBuilder =
    ConditionalRecipeBuilder().apply(builder)

fun lootPool(builder: LootPoolBuilder.() -> Unit): LootPool.Builder =
    LootPoolBuilder(LootPool.lootPool()).apply(builder).raw()

fun LanguageProvider.add(builder: TranslationBuilder.() -> Unit) =
    TranslationBuilder().apply(builder).build().forEach { (k, v) ->
        when (k) {
            is Block -> add(k, v)
            is Item -> add(k, v)
            is ItemStack -> add(k, v)
            is Enchantment -> add(k, v)
            is Effect -> add(k, v)
            is EntityType<*> -> add(k, v)
            is String -> add(k, v)
        }
    }

fun nbt(builder: CompoundBuilder.() -> Unit): CompoundNBT =
    CompoundBuilder().apply(builder).build()