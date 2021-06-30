package io.github.chaosunity.dgdsl.api

import net.devtech.arrp.impl.RuntimeResourcePackImpl
import net.devtech.arrp.json.recipe.JRecipe
import net.devtech.arrp.json.tags.JTag
import net.minecraft.util.Identifier

class DedicatedRuntimeResourcePack(private val modIdentifier: String, resourcePackId: String) :
    RuntimeResourcePackImpl(Identifier("$modIdentifier:$resourcePackId")) {
    operator fun set(id: String, recipe: JRecipe) {
        addRecipe(id(id), recipe)
    }

    operator fun set(id: String, tag: JTag) {
        addTag(id(id), tag)
    }

    private fun id(id: String) =
        Identifier("$modIdentifier:$id")
}