package io.github.chaos.dgdsl.examples

import com.google.gson.GsonBuilder
import io.github.chaos.dgdsl.lootPool
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate.toJson
import net.minecraft.block.Block
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.data.LootTableProvider
import net.minecraft.loot.LootParameterSets
import net.minecraft.loot.LootTable
import net.minecraft.loot.functions.CopyName
import net.minecraft.loot.functions.CopyNbt
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.LogManager
import java.io.IOException

import net.minecraft.loot.LootTableManager




/**
 * This class example is based on mcjty's loot table tutorial example.
 *
 * See https://wiki.mcjty.eu/modding/index.php?title=Tut14_Ep7
 */
abstract class BaseLoot(private val dataGenerator: DataGenerator) : LootTableProvider(dataGenerator) {
    companion object {
        private val LOGGER = LogManager.getLogger()
        private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    }

    protected val lootTables = mutableMapOf<Block, LootTable.Builder>()

    protected abstract fun addTables()

    protected fun createStandardTables(name: String, block: Block): LootTable.Builder {
        val builder = lootPool {
            name(name)
            rolls(1)
            entry {
                itemLoot(block) {
                    function {
                        copyName(CopyName.Source.BLOCK_ENTITY)
                        copyNbt(CopyNbt.Source.BLOCK_ENTITY) {
                            copy("inv", "BlockEntityTag.inv", CopyNbt.Action.REPLACE)
                            copy("energy", "BlockEntityTag.energy", CopyNbt.Action.REPLACE)
                        }
                        content {
                            // TODO: ADD MORE LOOT ENTRY BUILDERS
                        }
                    }
                    condition {
                        randomChance(0.1f)
                    }
                }
            }
            condition {
                surviveExplosion()
            }
        }

        return LootTable.lootTable().withPool(builder)
    }

    override fun run(p_200398_1_: DirectoryCache) {
        addTables()

        val tables = mutableMapOf<ResourceLocation, LootTable>()
        for (entry in lootTables.entries) {
            tables[entry.key.lootTable] = entry.value.setParamSet(LootParameterSets.BLOCK).build()
        }
        writeTables(p_200398_1_, tables)
    }

    private fun writeTables(cache: DirectoryCache, tables: Map<ResourceLocation, LootTable>) {
        val outputFolder = dataGenerator.outputFolder
        tables.forEach { (key, lootTable) ->
            val path = outputFolder.resolve("data/" + key.namespace + "/loot_tables/" + key.path + ".json")
            try {
                IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable), path)
            } catch (e: IOException) {
                LOGGER.error("Couldn't write loot table {}", path, e)
            }
        }
    }
}