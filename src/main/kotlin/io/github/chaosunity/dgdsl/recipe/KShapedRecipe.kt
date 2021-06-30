package io.github.chaosunity.dgdsl.recipe

import net.devtech.arrp.json.recipe.JKeys
import net.devtech.arrp.json.recipe.JPattern
import net.devtech.arrp.json.recipe.JRecipe
import net.devtech.arrp.json.recipe.JResult

fun shapedRecipe(`$1`: JPattern, `$2`: JKeys, `$3`: JResult, group: String? = null) =
    JRecipe.shaped(`$1`, `$2`, `$3`).apply { group?.let(::group) }