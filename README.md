# Data Generator DSL
![dgdsl](https://user-images.githubusercontent.com/43753315/109607086-a6c6f400-7b62-11eb-8f0a-68482d2ea46d.png)
> developer-friendly domain specific language for data generator purpose  

A DSL for data generator, makes your life better.

## Installation
TODO

## Usages
### ShapedRecipe

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
### ConditionalRecipe
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
// ...
```
### ConditionalAdvancement
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