package io.github.chaos.dgdsl.builder

import net.minecraft.advancements.ICriterionInstance

class CriterionBuilder {
    private val criterionMap = mutableMapOf<String, ICriterionInstance>()

    infix fun String.to(criterionIn: ICriterionInstance) {
        criterionMap[this] = criterionIn
    }

    fun build(): Map<String, ICriterionInstance> = criterionMap
}