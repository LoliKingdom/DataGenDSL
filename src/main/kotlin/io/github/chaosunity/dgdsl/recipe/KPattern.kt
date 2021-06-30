package io.github.chaosunity.dgdsl.recipe

import net.devtech.arrp.json.recipe.JPattern

fun pattern() =
    JPattern.pattern()

fun pattern(l1: String, l2: String, l3: String) =
    JPattern.pattern(l1, l2, l3)

fun pattern(l: Collection<String>) =
    JPattern.pattern(*l.toTypedArray())