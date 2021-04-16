package io.github.chaos.dgdsl.builder.nbt

import io.github.chaos.dgdsl.builder.AbstractBuilder
import io.github.chaos.dgdsl.builder.utils.IListInfixFunctions
import net.minecraft.nbt.*
import java.util.*

class ListBuilder(private val list: ListNBT = ListNBT()) : AbstractBuilder(), IListInfixFunctions {
    constructor(list: ListNBT = ListNBT(), elements: Collection<INBT>) : this(list) {
        list.addAll(elements)
    }

    fun boolean(boolean: Boolean) =
        list add ByteNBT.valueOf(boolean)

    fun byte(byte: Byte) =
        list add ByteNBT.valueOf(byte)

    fun short(short: Short) =
        list add ShortNBT.valueOf(short)

    fun int(int: Int) =
        list add IntNBT.valueOf(int)

    fun long(long: Long) =
        list add LongNBT.valueOf(long)

    fun uuid(uuid: UUID) =
        list add NBTUtil.createUUID(uuid)

    fun float(float: Float) =
        list add FloatNBT.valueOf(float)

    fun double(double: Double) =
        list add DoubleNBT.valueOf(double)

    fun string(string: String) =
        list add StringNBT.valueOf(string)

    fun byteArray(byteArray: ByteArray) =
        list add ByteArrayNBT(byteArray)

    fun intArray(intArray: IntArray) =
        list add IntArrayNBT(intArray)

    fun longArray(longArray: LongArray) =
        list add LongArrayNBT(longArray)

    fun compound(builder: CompoundBuilder.() -> Unit) =
        list add CompoundBuilder().apply(builder).build()

    fun list(builder: ListBuilder.() -> Unit) =
        list add ListBuilder().apply(builder).build()

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