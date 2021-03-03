package io.github.chaos.dgdsl.builder.utils

interface IListInfixFunctions {
    infix fun <T> MutableList<T>.add(another: T) =
        this.add(another)
}