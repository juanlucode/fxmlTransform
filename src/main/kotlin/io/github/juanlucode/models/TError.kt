package io.github.juanlucode.models

enum class TError(val message: String) {
    TARGET_CODE_ARGUMENT("Error in -t 'Target code' argument."),
    SOURCE_ARGUMENT("Error in -s 'source' argument.")
}