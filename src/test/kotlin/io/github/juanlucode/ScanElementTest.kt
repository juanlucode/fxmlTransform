package io.github.juanlucode

import javafx.embed.swing.JFXPanel
import org.junit.Test
import kotlin.test.assertEquals
import javafx.scene.control.*
import javafx.scene.layout.Region.*
import org.jdom2.Element
import java.lang.reflect.Method


class ScanElementTest {

    @Test
    fun mainTest(){

        // Needs to init javafx runtime
        JFXPanel()

        scanElement("VBox", "prefHeight")
    }

    private fun scanElement(elementName: String, prop: String){
        //val control = Class.forName("javafx.scene.control.${elementName}")

        val packs = listOf<String>( "javafx.scene.control",
                                    "javafx.scene.layout"
                                  )

        var control: Class<*>? = null

        val pack = packs.listIterator();
        while (control == null && pack.hasNext()){
            try{
                control = Class.forName("${pack.next()}.${elementName}")
            } catch ( ex: ClassNotFoundException){
                control = null
            }
        }

        println(control?.canonicalName)
        println("--------------------------")
        println("         METHODS          ")
        println("--------------------------")
        println()
/*
        for ( m in control!!.methods)
            if (m.parameterTypes.size > 0) println("${m.name} : ${m.parameterTypes[0].typeName}")
            //println("${m.name} : ${m.genericReturnType.typeName}")
*/

/*
We obtain the type of property data by examining the type that is returned by its get method (which has no parameter)
 */

        println(control!!.getMethod("get".plus(prop.capitalize())).genericReturnType.typeName)


    }

}
