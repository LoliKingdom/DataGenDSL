package io.github.chaosunity.dgdsl.builder.nbt

import io.github.chaosunity.dgdsl.builder.AbstractBuilder
import net.minecraft.nbt.*
import java.util.*

@Suppress("unused")
class CompoundBuilder(val compound: CompoundNBT = CompoundNBT()) : AbstractBuilder() {
    @Deprecated("Will replace with much more clear way.")
    fun compound(builder: () -> Pair<String, CompoundBuilder.() -> Unit>) {
        val (k, v) = builder.invoke()

        compound.put(k, CompoundBuilder().apply(v).build())
    }

    fun compound(key: String, builder: CompoundBuilder.() -> Unit) =
        compound { key to builder }

    inline fun <reified T> list(key: String, elements: List<T>) {
        val list = elements.map {
            when (T::class) {
                INBT::class -> it as INBT
                Boolean::class -> ByteNBT.valueOf(it as Boolean)
                Byte::class -> ByteNBT.valueOf(it as Byte)
                Short::class -> ShortNBT.valueOf(it as Short)
                Int::class -> IntNBT.valueOf(it as Int)
                Long::class -> LongNBT.valueOf(it as Long)
                UUID::class -> NBTUtil.createUUID(it as UUID)
                Float::class -> FloatNBT.valueOf(it as Float)
                Double::class -> DoubleNBT.valueOf(it as Double)
                String::class -> StringNBT.valueOf(it as String)
                ByteArray::class -> ByteArrayNBT(it as ByteArray)
                IntArray::class -> IntArrayNBT(it as IntArray)
                LongArray::class -> LongArrayNBT(it as LongArray)
                else -> throw IllegalArgumentException("${T::class.java.name} cannot convert into nbt.")
            }
        }

        compound.put(key, ListBuilder(elements = list).build())
    }

    fun list(key: String, builder: ListBuilder.() -> Unit) =
        list(key, ListBuilder().apply(builder).build())

    @Deprecated("Will replace with much more clear way.")
    fun boolean(builder: () -> Pair<String, Boolean>) {
        val (k, v) = builder.invoke()

        compound.putBoolean(k, v)
    }

    fun boolean(key: String, value: Boolean) =
        boolean { key to value }

    fun boolean(key: String, value: () -> Boolean) =
        boolean { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun byte(builder: () -> Pair<String, Byte>) {
        val (k, v) = builder.invoke()

        compound.putByte(k, v)
    }

    fun byte(key: String, value: Byte) =
        byte { key to value }

    fun byte(key: String, value: () -> Byte) =
        byte { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun short(builder: () -> Pair<String, Short>) {
        val (k, v) = builder.invoke()

        compound.putShort(k, v)
    }

    fun short(key: String, value: Short) =
        short { key to value }

    fun short(key: String, value: () -> Short) =
        short { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun int(builder: () -> Pair<String, Int>) {
        val (k, v) = builder.invoke()

        compound.putInt(k, v)
    }

    fun int(key: String, value: Int) =
        int { key to value }

    fun int(key: String, value: () -> Int) =
        int { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun long(builder: () -> Pair<String, Long>) {
        val (k, v) = builder.invoke()

        compound.putLong(k, v)
    }

    fun long(key: String, value: Long) =
        long { key to value }

    fun long(key: String, value: () -> Long) =
        long { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun uuid(builder: () -> Pair<String, UUID>) {
        val (k, v) = builder.invoke()

        compound.putUUID(k, v)
    }

    fun uuid(key: String, value: UUID) =
        uuid { key to value }

    fun uuid(key: String, value: () -> UUID) =
        uuid { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun float(builder: () -> Pair<String, Float>) {
        val (k, v) = builder.invoke()

        compound.putFloat(k, v)
    }

    fun float(key: String, value: Float) =
        float { key to value }

    fun float(key: String, value: () -> Float) =
        float { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun double(builder: () -> Pair<String, Double>) {
        val (k, v) = builder.invoke()

        compound.putDouble(k, v)
    }

    fun double(key: String, value: Double) =
        double { key to value }

    fun double(key: String, value: () -> Double) =
        double { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun string(builder: () -> Pair<String, String>) {
        val (k, v) = builder.invoke()

        compound.putString(k, v)
    }

    fun string(key: String, value: String) =
        string { key to value }

    fun string(key: String, value: () -> String) =
        string { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun byteArray(builder: () -> Pair<String, ByteArray>) {
        val (k, v) = builder.invoke()

        compound.putByteArray(k, v)
    }

    fun byteArray(key: String, value: ByteArray) =
        byteArray { key to value }

    fun byteArray(key: String, value: List<Byte>) =
        byteArray { key to value.toByteArray() }

    fun byteArray(key: String, value: () -> ByteArray) =
        byteArray { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun intArray(builder: () -> Pair<String, IntArray>) {
        val (k, v) = builder.invoke()

        compound.putIntArray(k, v)
    }

    fun intArray(key: String, value: IntArray) =
        intArray { key to value }

    fun intArray(key: String, value: List<Int>) =
        intArray { key to value.toIntArray() }

    fun intArray(key: String, value: () -> IntArray) =
        intArray { key to value.invoke() }

    @Deprecated("Will replace with much more clear way.")
    fun longArray(builder: () -> Pair<String, LongArray>) {
        val (k, v) = builder.invoke()

        compound.putLongArray(k, v)
    }

    fun longArray(key: String, value: LongArray) =
        longArray { key to value }

    fun longArray(key: String, value: List<Long>) =
        longArray { key to value.toLongArray() }

    fun longArray(key: String, value: () -> LongArray) =
        longArray { key to value.invoke() }

    fun build(): CompoundNBT =
        compound
}