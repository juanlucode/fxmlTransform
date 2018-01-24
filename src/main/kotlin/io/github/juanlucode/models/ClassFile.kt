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

        // infoComment
        infoComment()

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

    /*
        Insert comment head
     */
    protected fun infoComment(){
        sourceCode.appendln("// *******************************************************************************")
        sourceCode.appendln("// ${targetCode.toString()} view")
        sourceCode.appendln("// Automatically generated code by fxmlTransform")
        sourceCode.appendln("// By Juan Luis Garcia Rodríguez - 2018")
        sourceCode.appendln("// ")
        sourceCode.appendln("// https://github.com/juanlucode/fxmlTransform")
        sourceCode.appendln("// https://twitter.com/juanlucode")
        sourceCode.appendln("// https://www.linkedin.com/in/juan-luis-garc%C3%ADa-rodr%C3%ADguez-354919133/")
        sourceCode.appendln("// *******************************************************************************")
        sourceCode.appendln()
        sourceCode.appendln()
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


        val path = Paths.get(document.baseURI.removePrefix("file:")).parent.toAbsolutePath()

        return "${path.toString().plus(File.separator)}${className(document).plus(targetCode.ext)}"
    }

    /*
    write imports using node references
     */
    protected fun writeImports(document: Document) {
        getNodeRefs(document)
        val nodeIterator = nodes.values.iterator()
        while (nodeIterator.hasNext())
            sourceCode.appendln("import ${nodeIterator.next().name}${if (targetCode == TargetCode.JAVAFX) ';' else ' '}")

        sourceCode.appendln()
    }

    protected fun attrValue(node: String, attr: Attribute): String? {
        return when ( isAttrTypeString(node, attr) ){
            TAttr.STRING_TYPE -> {"\"${attr.value}\""}
            TAttr.NO_STRING_TYPE -> attr.value
            null -> null

        }

    }

    /*
    isAttrTypeString
    check if attribute allows String or char type (the most conflictive).
    returns:
    true - allows String or Char
    false - allows other type
    null - methods does not exist or doesn't allow any parameter (read only)
     */
    private fun isAttrTypeString(node: String, attr: Attribute): TAttr? {
        var attrType: TAttr? = null

        // search method only by primitives parameters
        val primitiveTypes = arrayOf<Class<*>>(
                Char::class.java,
                String::class.java,
                Byte::class.java,
                Short::class.java,
                Int::class.java,
                Long::class.java,
                Float::class.java,
                Double::class.java,
                Boolean::class.java
        )

        // checking String and Char
        var iType = -1
        while (++iType < 2 && attrType == null)
            try {
                nodes.get(node)?.getMethod("set".plus(attr.name.capitalize()), primitiveTypes[iType])
                attrType = TAttr.STRING_TYPE
            } catch (ex: NoSuchMethodException) {

            }

        // checking others
        iType = 1
        while (attrType == null && ++iType < primitiveTypes.size)
            try {
                nodes.get(node)?.getMethod("set".plus(attr.name.capitalize()), primitiveTypes[iType])
                attrType = TAttr.NO_STRING_TYPE
            } catch (ex: NoSuchMethodException) {

            }

        return attrType
    }

    /*
    get node references and load class in a nodes cache
     */
    private fun getNodeRefs(document: Document) {
        // Needs to init javafx runtime
        JFXPanel()

        lateinit var node: Class<*>

        if ( targetCode == TargetCode.JAVAFX ) {
            for (content in document.content) {
                try {
                    if (content.toString().contains("<?import"))
                        node = Class.forName(content.value)
                    nodes.put(node.simpleName, node)
                } catch (ex: ClassNotFoundException) {
                    ex.printStackTrace()
                }
            }
        } else if ( targetCode == TargetCode.TORNADOFX ) {
            try {
                node = Class.forName("tornadofx.*")
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
                    Files.newOutputStream(path, CREATE)).use { out -> out.write(data, 0, data.size) }
        } catch (x: IOException) {
            System.err.println(x)
            ok = false
        }

        return ok

    }

}