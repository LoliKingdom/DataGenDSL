package io.github.chaosunity.dgdsl.recipe

import io.github.chaosunity.dgdsl.util.DataGenDslMarker
import net.devtech.arrp.json.recipe.JKeys
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.Tag

@DataGenDslMarker
fun keys(`$`: JKeys.() -> Unit) =
    JKeys.keys().apply(`$`)

fun JKeys.key(key: String, vararg id: String) =
    key(key, ingredient { item(*id) })

fun JKeys.key(key: String, vararg item: ItemConvertible) =
    key(key, ingredient { item(*item) })

fun JKeys.key(key: String, vararg tag: Tag<Item>) =
    key(key, ingredient { tag(*tag) })
