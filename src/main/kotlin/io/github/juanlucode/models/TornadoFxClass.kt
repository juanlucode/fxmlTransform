package io.github.juanlucode.models

import org.jdom2.Content
import org.jdom2.Document
import org.jdom2.Element
import java.io.File

class TornadoFxClass() : ClassFile() {

    private val targetCode = TargetCode.TORNADOFX

    override fun generate(document: Document): File {
        /*
        To build name of new class file using document.baseURI.toString to get the name of origin
        fxml file, change the extension by .kt
         */

        println("Fichero origen: ${document.baseURI.toString()}")

        getImports(document, targetCode)

        writeElement(document.rootElement)

        println(sourceCode.toString())
        //return File(document.baseURI)
        return File("testTornadofx")
    }

    private fun writeElement(element: Element){

        // write its name and open brackets
        sourceCode.appendln("${element.name} {")

        // write its attributes
        for (attr in element.attributes)
            sourceCode.appendln("${attr.name} = ${attr.value}")

        // check if is a container
        if ( element.getChildren("children") != null){
            val childrenIt = element.children.iterator()
            while (childrenIt.hasNext())
                writeElement(childrenIt.next())
        }

        // write close brackets
        sourceCode.appendln("}")

    }
}
