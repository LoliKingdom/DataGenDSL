package io.github.chaosunity.dgdsl.recipe

import net.devtech.arrp.json.recipe.JIngredient
import net.devtech.arrp.json.recipe.JRecipe
import net.devtech.arrp.json.recipe.JStackedResult

fun stonecuttingRecipe(`$1`: JIngredient, `$2`: JStackedResult, group: String? = null) =
    JRecipe.stonecutting(`$1`, `$2`).apply { group?.let(::group) }