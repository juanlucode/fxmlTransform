package io.github.juanlucode.models

import org.jdom2.Document
import java.io.File

class JavaFxClass(): ClassFile() {
    override fun writeClassBody(document: Document) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generate(document: Document): File {
        println(document.baseURI)
        //return File(document.baseURI)
        return File("testjavafx")
    }

    override fun writeClassHead(document: Document) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}