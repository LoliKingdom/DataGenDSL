package io.github.chaos.dgdsl.builder

import com.google.gson.JsonObject
import net.minecraft.advancements.Advancement
import net.minecraft.data.IFinishedRecipe
import net.minecraftforge.common.crafting.ConditionalAdvancement
import net.minecraftforge.common.crafting.conditions.ICondition
import java.util.function.Consumer

class ConditionalAdvancementBuilder(private val builder: ConditionalAdvancement.Builder = ConditionalAdvancement.builder()) {
    fun condition(condition: ConditionBuilder.() -> ICondition) {
        builder.addCondition(condition.invoke(ConditionBuilder()))
    }

    fun advancement(callable: Consumer<Consumer<Advancement.Builder>>) {
        builder.addAdvancement(callable)
    }

    fun advancement(advancement: AdvancementBuilder.() -> Unit) {
        builder.addAdvancement(AdvancementBuilder().apply(advancement).raw())
    }

    fun advancement(fromRecipe: IFinishedRecipe) {
        builder.addAdvancement(fromRecipe)
    }

    fun build() =
        builder

    fun write(): JsonObject =
        builder.write()
}