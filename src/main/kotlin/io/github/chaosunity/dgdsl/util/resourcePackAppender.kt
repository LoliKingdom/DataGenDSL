package io.github.chaosunity.dgdsl.util

import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.json.recipe.JRecipe
import net.devtech.arrp.json.tags.JTag
import net.minecraft.util.Identifier

operator fun RuntimeResourcePack.set(id: Identifier, recipe: JRecipe) {
    addRecipe(id, recipe)
}

operator fun RuntimeResourcePack.set(id: Identifier, tag: JTag) {
    addTag(id, tag)
}