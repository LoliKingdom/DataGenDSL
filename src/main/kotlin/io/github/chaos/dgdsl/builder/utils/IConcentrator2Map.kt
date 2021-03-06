package io.github.chaos.dgdsl.builder.utils

class IConcentrator2Map<T, K, V> where T : Pair<K, V> {
    operator fun T.unaryPlus() =
        mutableMapOf(this)

    operator fun T.plus(another: T) =
        mutableMapOf(this, another)
}