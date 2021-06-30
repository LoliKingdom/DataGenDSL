package io.github.chaosunity.dgdsl

import io.github.chaosunity.dgdsl.api.DedicatedRuntimeResourcePack
import io.github.chaosunity.dgdsl.recipe.*
import io.github.chaosunity.dgdsl.util.register
import net.devtech.arrp.api.RRPCallback
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.launch.common.FabricLauncherBase
import net.minecraft.block.Blocks
import net.minecraft.util.shape.SimplePairList

object DataGenDSL : ModInitializer {
    const val MODID = "dgdsl"
    val RESOURCE_PACK = DedicatedRuntimeResourcePack(MODID, "")

    override fun onInitialize() {
        RESOURCE_PACK["shaped_recipe"] = shapedRecipe(pattern(
            "   ",
            " B ",
            "   ",
        ), keys {
            key("B", Blocks.DIRT)
        }, result(Blocks.DIAMOND_BLOCK))

        RESOURCE_PACK["stone_cutting"] = stonecuttingRecipe(
            ingredient(Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE),
            result(Blocks.ACACIA_BUTTON, 1)
        )

        if (FabricLauncherBase.getLauncher().isDevelopment) {
            RESOURCE_PACK.dump()
            register(RESOURCE_PACK)
        }
    }
}