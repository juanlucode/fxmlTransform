package io.github.juanlucode

import io.github.juanlucode.models.TError
import io.github.juanlucode.models.TargetCode
import io.github.juanlucode.models.Transformation
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.system.exitProcess

fun main(args: Array<String>) {


    // arguments required
    if (args.size == 0){
        help()
        exitProcess(0)
    }

    var i = 0
    lateinit var source: Path
    lateinit var target: TargetCode

    // args capture
    while (i < args.size){
        when (args[i]){
            // source: file or directory
            "-f", "-d" -> {
                try {
                    source = Paths.get(args[++i])
                } catch(ex: IllegalArgumentException){
                    error(TError.SOURCE_ARGUMENT)
                }
            }
            // target
            "-t" -> {
                try {
                    target = TargetCode.valueOf(args[++i].toUpperCase().trim())
                } catch (ex: IllegalArgumentException){
                    error(TError.TARGET_CODE_ARGUMENT)
                }
            }
            // help
            "-h" -> {
                help()
            }


        }
        i++
    }

    Transformation(source, target).process()

}


/**
 * Print help
 */
fun help(){
    println("FXML Transform")
    println("Options:")
    println("-f <fxml file>")
    println("-d <directory of fxml files>")
    println("-t <javafx | tornadofx>")
    println("-h this help.")
}

/**
 * Print error
 */
fun error(code: TError?){

    if (code == null){
        println("Undefined error.")
    } else {
        println(code.message)
    }

    when (code){
        TError.TARGET_CODE_ARGUMENT -> println()
        else -> println("Error.")
    }

}