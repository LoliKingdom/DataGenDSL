package io.github.chaosunity.dgdsl.builder

import io.github.chaosunity.dgdsl.pairToID
import net.minecraft.data.IFinishedRecipe
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.crafting.ConditionalAdvancement
import net.minecraftforge.common.crafting.ConditionalRecipe
import net.minecraftforge.common.crafting.conditions.ICondition
import java.util.function.Consumer

class ConditionalRecipeBuilder(private val builder: ConditionalRecipe.Builder = ConditionalRecipe.Builder()) : AbstractBuilder() {
    fun condition(condition: ConditionBuilder.() -> ICondition) {
        builder.addCondition(condition.invoke(ConditionBuilder()))
    }

    fun recipe(recipe: Consumer<IFinishedRecipe>.() -> Unit) {
        builder.addRecipe {
            recipe.invoke(it)
        }
    }

    fun genAdvancement(): ConditionalRecipeBuilder {
        builder.generateAdvancement()
        return this
    }

    fun genAdvancement(id: () -> Pair<String, String>) =
        genAdvancement(pairToID(id.invoke()))

    fun genAdvancement(id: ResourceLocation) {
        builder.generateAdvancement(id)
    }

    fun advancement(advancementBuilder: ConditionalAdvancement.Builder) {
        builder.setAdvancement(null, advancementBuilder)
    }

    fun advancement(id: ResourceLocation, advancementBuilder: ConditionalAdvancementBuilder.() -> Unit) {
        builder.setAdvancement(id, ConditionalAdvancementBuilder().apply(advancementBuilder).build())
    }

    fun advancement(id: Pair<String, String>, advancementBuilder: ConditionalAdvancementBuilder.() -> Unit) {
        builder.setAdvancement(pairToID(id), ConditionalAdvancementBuilder().apply(advancementBuilder).build())
    }

    fun build(consumer: Consumer<IFinishedRecipe>, id: ResourceLocation) =
        builder.build(consumer, id)

    fun build(consumer: Consumer<IFinishedRecipe>, id: () -> Pair<String, String>) =
        build(consumer, pairToID(id.invoke()))
}