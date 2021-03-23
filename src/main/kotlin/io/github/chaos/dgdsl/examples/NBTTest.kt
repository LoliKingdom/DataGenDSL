package io.github.chaos.dgdsl.examples

import io.github.chaos.dgdsl.nbt

object NBTTest {
    fun test() {
        val nbt = nbt {
            list("bruh") {
                compound {
                    int {
                        "brb" to 10
                    }
                }


            }
        }

        println(nbt)
    }
}