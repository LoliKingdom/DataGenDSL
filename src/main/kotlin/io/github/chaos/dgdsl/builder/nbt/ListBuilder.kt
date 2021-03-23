package io.github.chaos.dgdsl.builder.nbt

import net.minecraft.nbt.*
import java.util.*

class ListBuilder(private val list: ListNBT = ListNBT()) {
    constructor(list: ListNBT = ListNBT(), elements: Collection<INBT>) : this(list) {
        list.addAll(elements)
    }

    fun compound(builder: CompoundBuilder.() -> Unit) {
        list += CompoundBuilder().apply(builder).build()
    }

    /**
     *  Unary plus does not work on primitive numeral types, such as int, long ,or double.
     *  The only to bypass this limit is initializing builder with another list.
     */
    operator fun Any.unaryPlus() {
        list += when (this) {
            is Boolean -> ByteNBT.valueOf(this)
            is Byte -> ByteNBT.valueOf(this) // Unusable
            is Short -> ShortNBT.valueOf(this) // Unusable
            is Int -> IntNBT.valueOf(this) // Unusable
            is Long -> LongNBT.valueOf(this) // Unusable
            is UUID -> NBTUtil.func_240626_a_(this)
            is Float -> FloatNBT.valueOf(this) // Unusable
            is Double -> DoubleNBT.valueOf(this) // Unusable
            is String -> StringNBT.valueOf(this)
            is ByteArray -> ByteArrayNBT(this)
            is IntArray -> IntArrayNBT(this)
            is LongArray -> LongArrayNBT(this)
            is CompoundBuilder -> this.build()
            is ListBuilder -> this.build()
            else -> throw IllegalArgumentException("${this::class.java.name} cannot be converted into nbt.")
        }
    }

    fun build(): ListNBT =
        list
}