package io.github.chaosunity.dgdsl.examples

import io.github.chaosunity.dgdsl.DataGeneratorDSL.MODID
import io.github.chaosunity.dgdsl.lang
import net.minecraft.block.Blocks
import net.minecraft.data.DataGenerator
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.potion.Effects
import net.minecraftforge.common.data.LanguageProvider

class Lang(gen: DataGenerator) : LanguageProvider(gen, MODID, "en_us") {
    override fun addTranslations() {
        lang {
            Blocks.STONE to "Stone"
            Items.DIAMOND to "Diamond"
            Effects.POISON to "Poison"
            Enchantments.SHARPNESS to "Sharpness"
            EntityType.CAT to "Cat"
            "$MODID.test.unicode" to "\u0287s\u01DD\u2534 \u01DDpo\u0254\u1D09u\u2229"
        }
    }
}