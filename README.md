# Data Generator DSL

![dgdsl](https://user-images.githubusercontent.com/43753315/109607086-a6c6f400-7b62-11eb-8f0a-68482d2ea46d.png)
> developer-friendly domain specific language for data generator purpose

A DSL for data generator, makes your life better.

## Features

* Recipe DSL
* Loot Table DSL
* Advancement DSL
* NBT DSL
* Language DSL

## Installation

Groovy gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation fg.deobf('com.github.LoliKingdom:DataGenDSL:master-SNAPSHOT')
}
```

Kotlin gradle: 
```kotlin
repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(project.the<DependencyManagementExtension>()
        .deobf(project.dependencies.create('com.github.LoliKingdom:DataGenDSL:master-SNAPSHOT')))
}
```
## Table of Contents

<table>
    <tr><td width=33% valign=top>

* [Shaped Recipe](#shaped-recipe)
* [Conditional Recipe](#conditional-recipe)
* [Condition](#condition)
* [Conditional Advancement](#conditional-advancement)
* [Advancement](#advancement)
* [Condition](#condition)

</td><td width=33% valign=top>

* [Loot Table](#loot-table)
* [Loot Pool](#loot-pool)
* [Loot Entry](#loot-entry)
* [Loot Functions](#loot-functions)
* [Loot Conditions](#loot-conditions)
* [NBT](#nbt)

</td><td valign=top>

* [Lang](#lang)

</td></tr>
</table>

## Usages

### Shaped Recipe

```kotlin
recipe(resultItem, count) { // Count is set to 1 in default
    pattern {
        """
            xxx
            xxx
            xxx
        """
    } // You don't need to trim string, mod will done for you.

    key {
        'x' to YourItem // Creates a key
    } // You can add more keys if there are more.

    group("") // Your Group here

    criterion {
        "has_dirt" to hasItem(AnotherItem) // Creates a Criterion
    } // You can add more criterions if there are more.
}.build(conumer) // Remember to build
```

### Conditional Recipe

```kotlin
conditionalRecipe {
    condition {
        // Your Conditions here, examples in Condition part.
    }

    recipe {
        recipe(resultItem, count) {
            //...
        }.build(this) // add your shaped recipe here, build it with `this` keyword instead of `it`
    }

    advancement(advancementID) {
        // Your ConditionalAdvancement here, examples in ConditionalAdvancement part.
    }
}.genAdvancement().build(consumer, recipeID) // You can discard genAdvancement()
// or
conditionalRecipe {
    // ...
}.build(consumer) {
    "minecraft" to "conditional" // Creates a ResourceLocation
}
```

### Condition

```kotlin
// ... We take ConditionalRecipe as example
condition {
    and {
        not(modLoaded("minecraft")) +
                modLoaded {
                    +"eki_lib"
                    +"eki"
                } +
                itemExists {
                    "minecraft" to "dirt" // Creates a ResourceLocation
                }
    }
}
// or
condition {
    or {
        modLoaded("ekinomy") +
                True +
                False
    }
}
// or
condition {
    False
}
// ...
```

### Conditional Advancement

```kotlin
// ... We take ConditionalRecipe as example
adavancement(conditionalAdavancementID) {
    condition {
        // Your Condition here
    }

    advancement { // Actual Advancement
        // Your Advancement here, examples in Advancement part.
    }
}
// ...
```

### Advancement

```kotlin
// ... We take ConditionalAdvancement as example
advancement {
    parentID {
        "minecraft" to "test" // Creates a ResourceLocation
    }

    parent {
        parentAdvancement
    }

    /**
     * You must provide either parentID or parent
     */

    display {
        // Construct your AdvancementBuilder.DisplayInfo here
    }
}
// ...
```

## Loot Table

```kotlin
lootTable {
    parameterSet(paramSet)  // Set a predefined loot parameter set
    parameterSet {          // Or create your own loot parameters here
        optional(param1, param2) // Defines param1 and param2 as optional parameters, add more if you want
        required(param3, param4) // Defines param3 and param4 as optional parameters, add more if you want
    }

    pool { // Create a loot pool with exactly 1 roll in default and empty name. You can change rolls with `pool(roll = yourRollNumber)`, same as changing loot pool name.
        // See Loot Pool
    }

    /**
     *  Note: You can add pools with the following examples.
     */

    pool(1, 9f) { // Loot pool with binomial range of rolls, beware of the number type you passed in.

    }

    pool(1f, 7f) { // Loot pool with random range of rolls, beware of the number type you passed in.

    }

    function {
        // See Loot Functions
    }
}
```

## Loot Pool

```kotlin
// ... We take Loot Table's pool as example
pool {
    // You can add as many loot entries in a single pool
    itemLoot(Items.STICK)                       // Create a item loot entry, you can pass item or block.
    tagLoot(ItemTags.WOOL)                      // Create a tag loot entry
    tableLoot(ResourceLocation("grass_block"))  // Create a table loot entry
    alternativesLoot {                          // Create a alternative loot entry
        itemLoot(Items.ITEM_FRAME)
        // ... Add more loot entries here
    }
    dynamicLoot(ShulkerBoxBlock.CONTENTS)       // Create a dynamic loot entry
    emptyLoot {
        weight(2)   // Optional
        quality(2)  // Optional
    }

    function {
        // See Loot Functions
    }

    condition {
        // See Loot Conditions
    }

    bonusRoll(1f, 7f) // Optional
}
// ...
```

## Loot Entry

This part is tricky since not every loot entry is same as others, so check out source code if you're not sure.

```kotlin
// ... We take Loot Pool's item loot entry as example
itemLoot(Items.STICK) {
    function {
        // See Loot Functions
    }

    condition {
        // See Loot Conditions
    }

    weight(2)   // Optional
    quality(2)  // Optional
}
// ...
```

Alternatives loot entry is kinda different from other loot entries, see below:

```kotlin
alternativeLoot {
    condition {
        // See Loot Conditions
    }

    // You can add as many loot entries in a single alternative loot entry
    itemLoot(Items.STICK)                       // Create a item loot entry, you can pass item or block.
    tagLoot(ItemTags.WOOL)                      // Create a tag loot entry
    tableLoot(ResourceLocation("grass_block"))  // Create a table loot entry
    alternativesLoot {                          // Create a alternative loot entry
        itemLoot(Items.ITEM_FRAME)
        // ... Add more loot entries here
    }
    dynamicLoot(ShulkerBoxBlock.CONTENTS)       // Create a dynamic loot entry
    emptyLoot {
        weight(2)   // Optional
        quality(2)  // Optional
    }
}
```

Notice that alternative loot entry does not have `function`, `weight` and `quality`.

## Loot Functions

There are lots of loot functions, checkout source code for more info.

```kotlin
// ... We take Loot Table's function as example
function {
    copyName(CopyName.Source.BLOCK_ENTITY)
    copyNbt(CopyNbt.Source.BLOCK_ENTITY) {
        copy("inv", "BlockEntityTag.inv", CopyNbt.Action.REPLACE)
        copy("energy", "BlockEntityTag.energy", CopyNbt.Action.REPLACE)
    }
    content {
        itemLoot(Items.ACACIA_BOAT)
    }
}
// ...
```

## Loot Conditions

There are lots of loot conditions, checkout source code for more info.

```kotlin
// ... We take Loot Pool's conditions as example
condition {
    randomChance(0.1f)
    surviveExplosion()
}
// ...
```

## NBT

You can make nbt compound with data gen dsl, here's an example:

```kotlin
nbt { // this top-level-function creates a nbt compound
    int("integer", 1) // define key "integer" with integer value 1
    int("integer") {  // or you can defines a key in this way
        // Evaluate your integer value here
    }

    compound("compound") { // define key "compound" with a nbt compound
        // ...
    }

    list("list", listOf("yap", "this", "is", "example")) // define key "list" with list of strings
    list("compoundList") { // You might want to define a compound list in this way
        compound {
            // ...
        }
    }
    list("ughh") { // Highly not recommended
        number(1)
        number(2)
    }

    intArray("intArray", listOf(1, 2, 3)) // defines key "intArray" with a list of integers
}
```

## Lang
```kotlin
// ... We take LanguageProvider's function `addTranslations` as example
override fun addTranslations() {
    add {
        Blocks.STONE to "Stone"
        Items.DIAMOND to "Diamond"
        Effects.POISON to "Poison"
        Enchantments.SHARPNESS to "Sharpness"
        EntityType.CAT to "Cat"
        "$MODID.test.unicode" to "\u0287s\u01DD\u2534 \u01DDpo\u0254\u1D09u\u2229"
    }
}
```
