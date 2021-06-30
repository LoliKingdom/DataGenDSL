package io.github.chaosunity.dgdsl.recipe

import net.devtech.arrp.json.recipe.JIngredient
import net.devtech.arrp.json.recipe.JRecipe
import net.devtech.arrp.json.recipe.JResult

fun blastingRecipe(
    `$1`: JIngredient,
    `$2`: JResult,
    group: String? = null,
    cookingTime: Int? = null,
    experience: Float? = null,
) = JRecipe.blasting(`$1`, `$2`).apply {
        group?.let(::group)
        cookingTime?.let(::cookingTime)
        experience?.let(::experience)
    }