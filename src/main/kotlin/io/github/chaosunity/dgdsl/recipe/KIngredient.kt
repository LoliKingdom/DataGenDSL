package io.github.chaosunity.dgdsl.recipe

import io.github.chaosunity.dgdsl.util.DataGenDslMarker
import net.devtech.arrp.json.recipe.JIngredient
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.ServerTagManagerHolder
import net.minecraft.tag.Tag
import net.minecraft.util.registry.Registry

@DataGenDslMarker
fun ingredient(`$`: JIngredient.() -> Unit) =
    JIngredient.ingredient().apply(`$`)

fun ingredient(vararg id: String) =
    ingredient { item(*id) }

fun ingredient(vararg item: ItemConvertible) =
    ingredient { item(*item) }

fun ingredient(vararg tag: Tag<Item>) =
    ingredient { tag(*tag) }

fun ingredient(vararg ingredient: JIngredient) =
    ingredient { ingredient(*ingredient) }

fun JIngredient.item(vararg id: String): Unit =
    id.forEach(::item)

fun JIngredient.item(vararg item: ItemConvertible): Unit =
    item.map(ItemConvertible::asItem).forEach(::item)

fun JIngredient.tag(vararg tag: Tag<Item>): Unit =
    tag.map {
        ServerTagManagerHolder.getTagManager()
            .getTagId(Registry.ITEM_KEY, it) { IllegalStateException("Unknown item tag") }.toString()
    }.forEach(::tag)

fun JIngredient.ingredient(vararg ingredient: JIngredient): Unit =
    ingredient.forEach(::add)
