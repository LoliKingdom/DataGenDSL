package io.github.chaos.dgdsl.builder.nbt

import io.github.chaos.dgdsl.builder.AbstractBuilder
import net.minecraft.nbt.*
import java.util.*

@Suppress("unused")
class ListBuilder(private val list: ListNBT = ListNBT()) : AbstractBuilder() {
    constructor(list: ListNBT = ListNBT(), elements: Collection<INBT>) : this(list) {
        list.addAll(elements)
    }

    private fun add(nbt: INBT) {
        list += nbt
    }

    fun boolean(boolean: Boolean) =
        add(ByteNBT.valueOf(boolean))

    fun byte(byte: Byte) =
        add(ByteNBT.valueOf(byte))

    fun short(short: Short) =
        add(ShortNBT.valueOf(short))

    fun int(int: Int) =
        add(IntNBT.valueOf(int))

    fun long(long: Long) =
        add(LongNBT.valueOf(long))

    fun uuid(uuid: UUID) =
        add(NBTUtil.createUUID(uuid))

    fun float(float: Float) =
        add(FloatNBT.valueOf(float))

    fun double(double: Double) =
        add(DoubleNBT.valueOf(double))

    fun string(string: String) =
        add(StringNBT.valueOf(string))

    fun byteArray(byteArray: ByteArray) =
        add(ByteArrayNBT(byteArray))

    fun intArray(intArray: IntArray) =
        add(IntArrayNBT(intArray))

    fun longArray(longArray: LongArray) =
        add(LongArrayNBT(longArray))

    fun compound(builder: CompoundBuilder.() -> Unit) =
        add(CompoundBuilder().apply(builder).build())

    fun list(builder: ListBuilder.() -> Unit) =
        add(ListBuilder().apply(builder).build())

    /**
     *  Unary plus does not work on primitive numeral types, such as int, long ,or double.
     *  The only to bypass this limit is initializing builder with another list.
     */
    operator fun Any.unaryPlus() {
        list += when (this) {
            is Boolean -> ByteNBT.valueOf(this)
//            is Byte -> ByteNBT.valueOf(this) // Unusable
//            is Short -> ShortNBT.valueOf(this) // Unusable
//            is Int -> IntNBT.valueOf(this) // Unusable
//            is Long -> LongNBT.valueOf(this) // Unusable
            is UUID -> NBTUtil.createUUID(this)
//            is Float -> FloatNBT.valueOf(this) // Unusable
//            is Double -> DoubleNBT.valueOf(this) // Unusable
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