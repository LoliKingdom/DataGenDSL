package io.github.chaos.dgdsl

import io.github.chaos.dgdsl.examples.Recipes
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(DataGeneratorDSL.MODID)
object DataGeneratorDSL {
    const val MODID = "data_gen_dsl"

    private const val DEBUG_MODE = true

    private val LOGGER = LogManager.getLogger()

    init {
        MOD_BUS.addListener(::setup)

        MOD_BUS.addListener(::onGatherData)
    }

    private fun setup(event: FMLCommonSetupEvent) {
    }

    private fun onGatherData(event: GatherDataEvent) {
        if (!DEBUG_MODE)
            return

        val generator = event.generator

        if (event.includeServer()) {
            generator.addProvider(Recipes(generator))
        }
    }
}