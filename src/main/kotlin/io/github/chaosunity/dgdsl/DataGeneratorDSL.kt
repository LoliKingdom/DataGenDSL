package io.github.chaosunity.dgdsl

import io.github.chaosunity.dgdsl.examples.*
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(DataGeneratorDSL.MODID)
object DataGeneratorDSL {
    const val MODID = "data_gen_dsl"

    private val LOGGER = LogManager.getLogger()

    init {
        MOD_BUS.addListener(::setup)

        MOD_BUS.addListener(::onGatherData)
    }

    private fun setup(event: FMLCommonSetupEvent) {
    }

    private fun onGatherData(event: GatherDataEvent) {
        NBTTest.test()

        val generator = event.generator

        generator.addProvider(Recipes(generator))

        generator.addProvider(Lang(generator))

        generator.addProvider(LootTable(generator))

        val blockTagProvider = BlockTag(generator, event.existingFileHelper)

        generator.addProvider(blockTagProvider)

        generator.addProvider(ItemTag(generator, blockTagProvider, event.existingFileHelper))
    }
}