package io.github.juanlucode.models

import javafx.embed.swing.JFXPanel
import org.jdom2.Attribute
import org.jdom2.Document
import java.io.File
import java.lang.reflect.Type

abstract class ClassFile() {

    protected val sourceCode: StringBuilder = StringBuilder()

    private var nodes = hashMapOf<String, Class<*>>()

    abstract fun generate(document: Document): File

    abstract protected fun writeClassHead(document: Document)

    abstract protected fun writeClassBody(document: Document)

    /**
     * Get the class name from the name of the source fxml file
     */
    protected fun className(document: Document): String {
        val originPath = document.baseURI.toString()

        return originPath.substring(
                originPath.lastIndexOf(File.separator) + 1,
                originPath.lastIndexOf('.')
        ).capitalize()
    }

    /*
    write imports using node references
     */
    protected fun writeImports(document: Document, target: TargetCode) {
        getNodeRefs(document)
        val nodeIterator = nodes.values.iterator()
        while (nodeIterator.hasNext())
            sourceCode.appendln("import ${nodeIterator.next().name}${if (target == TargetCode.JAVAFX) ';' else ' '}")

        sourceCode.appendln()
    }

    protected fun attrValue(node: String, attr: Attribute): String {
        return if ( attrType(node, attr).typeName.equals("String")) "\"${attr.value}\"" else attr.value
    }

    /*
    obtain the type of property data by examining the type that is returned by its get method
    (which has no parameter, the best for using with getMethod)
     */
    private fun attrType(node: String, attr: Attribute ): Type {
        return nodes.get(node)?.getMethod("get".plus(attr.name.capitalize()))!!.genericReturnType
    }

    /*
    get node references and load class in a nodes cache
     */
    private fun getNodeRefs(document: Document) {
        // Needs to init javafx runtime
        JFXPanel()

        lateinit var node: Class<*>
        for (content in document.content) {
            try {
                if (content.toString().contains("<?import"))
                node = Class.forName(content.value)
                nodes.put(node.canonicalName, node)
            } catch (ex: ClassNotFoundException) {
                ex.printStackTrace()
            }
        }
    }

}