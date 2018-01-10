package io.github.juanlucode.models

import org.jdom2.Document
import java.io.File

class TornadoFxClass() : ClassFile() {


    override fun generate(document: Document): File {
        /*
        To build name of new class file using document.baseURI.toString to get the name of origin
        fxml file, change the extension by .kt
         */

        println("Fichero origen: ${document.baseURI.toString()}")

        val rootNode = document.rootElement

        println("Root: ${rootNode.name}")

        println("Atributos de root")
        for (attr in rootNode.attributes) println("${attr.name}: ${attr.value}")

        println("Hijos")
        for (child in rootNode.children){
            for (child2 in child.children) {
                println(child2.name)
                for (attr in child2.attributes) println("\t ${attr.name}: ${attr.value}")
            }
        }

        //return File(document.baseURI)
        return File("testTornadofx")
    }
}
