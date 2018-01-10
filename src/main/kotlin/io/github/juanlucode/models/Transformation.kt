package io.github.juanlucode.models

import java.nio.file.Path

data class Transformation(val source: List<Path>, val target: TargetCode) {

    fun process(){
        println("doing transformation...")

        for (file in source){
            FxmlFile(file).transform(target)
        }
    }
}