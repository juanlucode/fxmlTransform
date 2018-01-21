package io.github.juanlucode.models

import javafx.embed.swing.JFXPanel
import org.jdom2.Attribute
import org.jdom2.Document
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardOpenOption.*
import java.nio.file.*
import java.io.*




abstract class ClassFile(open val targetCode: TargetCode) {

    private lateinit var path: Path

    private var nodes = hashMapOf<String, Class<*>>()

    protected val sourceCode: StringBuilder = StringBuilder()

    //abstract fun generate(document: Document): File

    abstract protected fun writeClassHead(document: Document)

    abstract protected fun writeClassBody(document: Document)

    fun generate(document: Document): Boolean {
        val ok = true

        // imports
        writeImports(document, targetCode)

        // class head
        writeClassHead(document)

        // class body
        writeClassBody(document)

        // close class brackets
        sourceCode.appendln("}")

        println(sourceCode.toString())

        createFile(document)

        return ok
    }

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

    protected fun fileName(document: Document): String {
        // todo get the origin path
        return "./${className(document).plus(targetCode.ext)}"
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
        return if (isAttrTypeString(node, attr)) "\"${attr.value}\"" else attr.value
    }

    /*
    DISCARD
    obtain the type of property data by examining the type that is returned by its get method
    (which has no parameter, the best for using with getMethod)
     */
    private fun isAttrTypeString(node: String, attr: Attribute): Boolean {
        var isString = false

        // search method only by primitives parameters
        val primitiveTypes = arrayOf<Class<*>>(
                //Byte::class.java,
                //Short::class.java,
                //Int::class.java,
                //Long::class.java,
                //Float::class.java,
                //Double::class.java,
                //Boolean::class.java,
                Char::class.java,
                String::class.java
        )

        val typeIt = primitiveTypes.iterator()
        while (false == isString && typeIt.hasNext())
            try {
                nodes.get(node)?.getMethod("set".plus(attr.name.capitalize()), typeIt.next())
                isString = true
            } catch (ex: NoSuchMethodException) {

            }

        return isString
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
                nodes.put(node.simpleName, node)
            } catch (ex: ClassNotFoundException) {
                ex.printStackTrace()
            }
        }
    }

    private fun createFile(document: Document): Boolean {
        var ok = true
        // Convert the string to a
        // byte array.
        val data =  sourceCode.toString().toByteArray()
        val path = Paths.get(fileName(document))

        try {
            BufferedOutputStream(
                    Files.newOutputStream(path, CREATE, APPEND)).use { out -> out.write(data, 0, data.size) }
        } catch (x: IOException) {
            System.err.println(x)
            ok = false
        }

        return ok

    }

}