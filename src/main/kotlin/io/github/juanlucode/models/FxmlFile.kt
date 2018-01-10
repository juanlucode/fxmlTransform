package io.github.juanlucode.models

import org.jdom2.Document
import org.jdom2.input.SAXBuilder
import java.io.File
import java.nio.file.Path

data class FxmlFile(val path: Path) {

    fun transform(target: TargetCode): File {

        // read xml file
        val document = readFxml()

        // depending target code..
        val classFile = when(target){
            TargetCode.JAVAFX -> JavaFxClass().generate(document)
            TargetCode.TORNADOFX -> TornadoFxClass().generate(document)
        }

        return classFile
    }

    private fun readFxml(): Document{
        return SAXBuilder().build(path.toFile())
    }
}