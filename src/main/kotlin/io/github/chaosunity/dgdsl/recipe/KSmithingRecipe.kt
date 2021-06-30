package io.github.chaosunity.dgdsl.recipe

import net.devtech.arrp.json.recipe.JIngredient
import net.devtech.arrp.json.recipe.JRecipe
import net.devtech.arrp.json.recipe.JResult

fun smithingRecipe(`$1`: JIngredient, `$2`: JIngredient, `$3`: JResult, group: String? = null) =
    JRecipe.smithing(`$1`, `$2`, `$3`).apply { group?.let(::group) }