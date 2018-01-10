package io.github.juanlucode.models

import org.jdom2.Document
import java.io.File

abstract class ClassFile {
    abstract fun generate(document: Document): File
}