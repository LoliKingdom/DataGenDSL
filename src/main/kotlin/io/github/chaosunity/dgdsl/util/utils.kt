package io.github.chaosunity.dgdsl.util

import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack
import net.minecraft.item.Item
import net.minecraft.tag.ServerTagManagerHolder
import net.minecraft.tag.Tag
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

fun <T> getId(entry: Map.Entry<Tag<T>, RegistryKey<out Registry<T>>>) =
    ServerTagManagerHolder.getTagManager()
        .getTagId(entry.value, entry.key) { IllegalStateException("Unknown item tag") }

fun <T> Tag<T>.getId(registry: RegistryKey<out Registry<T>>) =
    ServerTagManagerHolder.getTagManager()
        .getTagId(registry, this) { IllegalStateException("Unknown item tag") }.toString()

fun Tag<Item>.tagId() =
    getId(Registry.ITEM_KEY)

fun register(resourcePack: RuntimeResourcePack) =
    RRPCallback.BEFORE_VANILLA.register { it.add(resourcePack) }