package io.github.juanlucode.models

import org.jdom2.Document
import org.jdom2.Element
import java.io.File

class TornadoFxClass() : ClassFile() {

    private val targetCode = TargetCode.TORNADOFX

    override fun generate(document: Document): File {

        // imports
        writeImports(document, targetCode)

        // class head
        writeClassHead(document)

        // class body
        writeClassBody(document)

        // close class brackets
        sourceCode.appendln("}")

        println(sourceCode.toString())
        //return File(document.baseURI)
        return File("testTornadofx")
    }

    override protected fun writeClassHead(document: Document) {
        //class Intro: View() {
        sourceCode.appendln("class ${className(document)}: View() {")
    }

    override protected fun writeClassBody(document: Document) {
        // tab control
        val tab = Tab()

        tab.inc()
        // tab level class header begin
        sourceCode.appendln(tab.format("override val root = "))

        // write every element of fxml file
        writeElement(document.rootElement, tab)

        // tab level class header end
        tab.dec()

    }

    private fun writeElement(element: Element, tab: Tab) {

        if (false == element.name.equals("children")) {
            // write its name and open brackets
            tab.inc()
            sourceCode.appendln(tab.format("${element.name.toLowerCase()} {"))
            tab.inc()
            // write its attributes
            for (attr in element.attributes) {
                sourceCode.appendln(tab.format("${attr.name} = ${attrValue(element.name, attr)}"))
            }
            tab.dec()
        }
        // check if is a container
        if (element.getChildren("children") != null) {
            val childrenIt = element.children.iterator()
            while (childrenIt.hasNext())
                writeElement(childrenIt.next(), tab)
        }

        if (false == element.name.equals("children")) {
            // write close brackets
            sourceCode.appendln(tab.format("}"))
            tab.dec()
        }
    }
}
