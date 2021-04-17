package io.github.chaosunity.dgdsl.builder

import io.github.chaosunity.dgdsl.pairToID
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.util.ResourceLocation

class AdvancementRewardBuilder(private val builder: AdvancementRewards.Builder = AdvancementRewards.Builder()) : AbstractBuilder() {
    fun experience(experience: Int) {
        builder.addExperience(experience)
    }

    fun recipe(id: ResourceLocation) {
        builder.addRecipe(id)
    }

    fun recipe(id: () -> Pair<String, String>) {
        builder.addRecipe(pairToID(id.invoke()))
    }

    fun builder(): AdvancementRewards.Builder =
        builder

    fun build(): AdvancementRewards =
        builder.build()
}