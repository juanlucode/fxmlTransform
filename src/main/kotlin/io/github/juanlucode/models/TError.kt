package io.github.juanlucode.models

enum class TError(val message: String) {
    TARGET_CODE_ARGUMENT("Error in -t 'Target code' argument."),
    TARGET_REQUIRED("Error: target is required."),
    SOURCE_ARGUMENT("Error in -s 'source' argument."),
    SOURCE_MUST_BE_FILE("Error: Source must be a fxml file."),
    SOURCE_REQUIRED("Error: source is required.")
}