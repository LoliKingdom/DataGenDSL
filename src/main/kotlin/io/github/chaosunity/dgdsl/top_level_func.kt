package io.github.chaosunity.dgdsl

import io.github.chaosunity.dgdsl.builder.ConditionalRecipeBuilder
import io.github.chaosunity.dgdsl.builder.RecipeBuilder
import io.github.chaosunity.dgdsl.builder.TranslationBuilder
import io.github.chaosunity.dgdsl.builder.loot.LootTableBuilder
import io.github.chaosunity.dgdsl.builder.nbt.CompoundBuilder
import io.github.chaosunity.dgdsl.builder.tag.BlockTagBuilder
import io.github.chaosunity.dgdsl.builder.tag.ItemTagBuilder
import net.minecraft.block.Block
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.ItemTagsProvider
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootTable
import net.minecraft.nbt.CompoundNBT
import net.minecraft.potion.Effect
import net.minecraft.util.IItemProvider
import net.minecraftforge.common.data.LanguageProvider

fun recipe(result: IItemProvider, resultCount: Int = 1, builder: RecipeBuilder.() -> Unit): RecipeBuilder =
    RecipeBuilder(ShapedRecipeBuilder(result, resultCount), result).apply(builder)

fun conditionalRecipe(builder: ConditionalRecipeBuilder.() -> Unit): ConditionalRecipeBuilder =
    ConditionalRecipeBuilder().apply(builder)

fun lootTable(builder: LootTableBuilder.() -> Unit): LootTable.Builder =
    LootTableBuilder(LootTable.lootTable()).apply(builder).build()

fun LanguageProvider.lang(builder: TranslationBuilder.() -> Unit) =
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

fun ItemTagsProvider.tag(builder: ItemTagBuilder.() -> Unit) =
    ItemTagBuilder(this).apply(builder)

fun BlockTagsProvider.tag(builder: BlockTagBuilder.() -> Unit) =
    BlockTagBuilder(this).apply(builder)

fun nbt(builder: CompoundBuilder.() -> Unit): CompoundNBT =
    CompoundBuilder().apply(builder).build()