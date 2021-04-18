package io.github.chaosunity.dgdsl.builder

import com.google.gson.JsonObject
import io.github.chaosunity.dgdsl.pairToID
import net.minecraft.advancements.*
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import java.util.function.Consumer

class AdvancementBuilder(private val builder: Advancement.Builder = Advancement.Builder.advancement()) :
    AbstractBuilder() {
    lateinit var parentIdentifier: ResourceLocation
    lateinit var parentAdvancement: Advancement

    fun parent(parent: Advancement) {
        parentAdvancement = parent
        builder.parent(parent)
    }

    fun parentID(id: ResourceLocation) {
        parentIdentifier = id
        builder.parent(id)
    }

    fun parentID(id: () -> Pair<String, String>) {
        parentID(pairToID(id.invoke()))
    }

    fun display(
        item: IItemProvider,
        title: ITextComponent,
        description: ITextComponent,
        background: ResourceLocation?,
        frameType: FrameType,
        showToast: Boolean,
        announceToChat: Boolean,
        hidden: Boolean
    ) {
        builder.display(
            DisplayInfo(
                ItemStack(item),
                title,
                description,
                background,
                frameType,
                showToast,
                announceToChat,
                hidden
            )
        )
    }

    fun display(
        stack: ItemStack,
        title: ITextComponent,
        description: ITextComponent,
        background: ResourceLocation?,
        frameType: FrameType,
        showToast: Boolean,
        announceToChat: Boolean,
        hidden: Boolean
    ) {
        builder.display(
            DisplayInfo(
                stack,
                title,
                description,
                background,
                frameType,
                showToast,
                announceToChat,
                hidden
            )
        )
    }

    fun reward(reward: AdvancementRewardBuilder.() -> Unit) {
        builder.rewards(AdvancementRewardBuilder().apply(reward).builder())
    }

    fun criterion(criterion: CriterionBuilder.() -> Unit) {
        val criterions = CriterionBuilder().apply(criterion).build()

        criterions.forEach(builder::addCriterion)
    }

    fun criterion(criterionKey: () -> Pair<String, Criterion>) {
        val (key, criterion) = criterionKey.invoke()
        builder.addCriterion(key, criterion)
    }

    fun requirementsStrategy(requirementsStrategy: IRequirementsStrategy) {
        builder.requirements(requirementsStrategy)
    }

    inline fun resolveParent(crossinline lookup: (ResourceLocation) -> Advancement): Boolean =
        if (!isIdInit()) true
        else {
            if (!isParentInit()) {
                parentAdvancement = lookup.invoke(parentIdentifier)
            }

            isParentInit()
        }

    fun isParentInit(): Boolean =
        this::parentAdvancement.isInitialized

    fun isIdInit(): Boolean =
        this::parentIdentifier.isInitialized

    fun build(id: ResourceLocation): Advancement =
        builder.build(id)

    fun build(id: () -> Pair<String, String>): Advancement =
        build(pairToID(id.invoke()))

    fun raw(): Advancement.Builder =
        builder

    fun register(consumer: Consumer<Advancement>, id: String): Advancement =
        builder.save(consumer, id)

    fun serialize(): JsonObject =
        builder.serializeToJson()

    fun writeTo(buf: PacketBuffer) =
        builder.serializeToNetwork(buf)

    fun readFrom(buf: PacketBuffer) =
        Advancement.Builder.fromNetwork(buf)

    fun getCriteria(): Map<String, Criterion> =
        builder.criteria

    override fun toString(): String =
        builder.toString()
}