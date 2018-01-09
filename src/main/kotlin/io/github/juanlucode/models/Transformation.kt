package io.github.juanlucode.models

import java.nio.file.Path

data class Transformation(val source: Path, val target: TargetCode) {

    fun process(){
        println("doing transformation...")
    }
}