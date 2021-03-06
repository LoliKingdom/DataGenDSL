package io.github.chaos.dgdsl.builder.utils

interface IConcentrator2List<T> {
    operator fun T.unaryPlus() =
        mutableListOf(this)

    operator fun T.plus(another: T) =
        mutableListOf(this, another)

    companion object {
        fun <T> gen(): IConcentrator2List<T> =
            object : IConcentrator2List<T> {}
    }
}