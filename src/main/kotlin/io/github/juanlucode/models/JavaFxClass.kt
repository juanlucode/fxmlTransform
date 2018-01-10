package io.github.juanlucode.models

import org.jdom2.Document
import java.io.File

class JavaFxClass(): ClassFile() {
    override fun generate(document: Document): File {
        println(document.baseURI)
        //return File(document.baseURI)
        return File("testjavafx")
    }

}