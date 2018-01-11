package io.github.juanlucode.models

import org.jdom2.Document
import org.jdom2.Element
import java.io.File

class TornadoFxClass() : ClassFile() {


    override fun generate(document: Document): File {
        /*
        To build name of new class file using document.baseURI.toString to get the name of origin
        fxml file, change the extension by .kt
         */

        println("Fichero origen: ${document.baseURI.toString()}")

        writeElement(document.rootElement)

        //return File(document.baseURI)
        return File("testTornadofx")
    }

    private fun writeElement(element: Element){

        // write its name and open brackets
        println("${element.name} {")

        // write its attributes
        for (attr in element.attributes)
            println("${attr.name}: ${attr.value}")

        // check if is a container
        if ( element.getChildren("children") != null){
            val childrenIt = element.children.iterator()
            while (childrenIt.hasNext())
                writeElement(childrenIt.next())
        }

        // write close brackets
        println("}")

    }
}
