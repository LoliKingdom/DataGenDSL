package io.github.chaosunity.dgdsl.tag

import io.github.chaosunity.dgdsl.util.getId
import net.devtech.arrp.json.tags.JTag
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.Tag
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

fun tag(`$`: JTag.() -> Unit) =
    JTag.tag().apply(`$`)

fun replacingTag(`$`: JTag.() -> Unit) =
    JTag.replacingTag().apply(`$`)

fun JTag.add(vararg item: ItemConvertible): Unit =
    item.map(ItemConvertible::asItem).map(Registry.ITEM::getId).forEach(::add)

fun <T> JTag.tag(vararg tag: Tag<T>, key: RegistryKey<out Registry<T>>): Unit =
    tag.associateWith { key }.map(::getId).forEach(::tag)
