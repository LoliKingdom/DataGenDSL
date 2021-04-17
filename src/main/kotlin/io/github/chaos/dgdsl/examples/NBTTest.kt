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
                compound {
                    boolean("this_is_bool") {
                        true
                    }
                }
            }
            int("integerOfCourse", 1)
        }

        println(nbt)
    }
}