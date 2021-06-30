package io.github.chaosunity.dgdsl.recipe

import net.devtech.arrp.json.recipe.JResult
import net.minecraft.item.ItemConvertible

fun result(item: ItemConvertible) =
    JResult.item(item.asItem())

fun result(id: String) =
    JResult.result(id)

fun result(item: ItemConvertible, count: Int) =
    JResult.itemStack(item.asItem(), count)

fun result(id: String, count: Int) =
    JResult.stackedResult(id, count)
