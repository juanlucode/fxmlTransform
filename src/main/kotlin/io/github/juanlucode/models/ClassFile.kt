package io.github.juanlucode.models

import org.jdom2.Document
import java.io.File
import java.nio.file.Path

abstract class ClassFile() {

    val sourceCode: StringBuilder = StringBuilder()

    abstract fun generate(document: Document): File

    /**
     * Get the class name from the name of the source fxml file
     */
    protected fun className(document: Document): String {
        val originPath = document.baseURI.toString()

        return originPath.substring(
                    originPath.lastIndexOf(File.separator),
                    originPath.lastIndexOf('.')
                ).capitalize()
    }

    protected fun getImports(document: Document, target: TargetCode ){

        for (content in document.content)
            if (content.toString().contains("<?import"))
                sourceCode.appendln("import ${content.value}${if (target == TargetCode.JAVAFX) ';' else ' '}")
    }

}