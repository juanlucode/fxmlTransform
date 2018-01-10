package io.github.juanlucode

import io.github.juanlucode.models.TError
import io.github.juanlucode.models.TargetCode
import io.github.juanlucode.models.Transformation
import java.nio.file.Files
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
    val source: MutableList<Path> = mutableListOf()
    var target: TargetCode? = null

    // args capture
    while (i < args.size){
        when (args[i]){
            // source: file or directory
            "-s" -> {
                try {
                    // checking sources must be file
                    // taking nexts args as files until next pointer option (-)
                    while (false == args[i + 1].startsWith("-")) {
                        i++
                        val item = Paths.get(args[i])
                        // pick only files with .fxml extension
                        if ( Files.isRegularFile(item) && item.toString().endsWith(".fxml") )
                            // correct
                            source.add(item)
                        else
                            error(TError.SOURCE_MUST_BE_FILE)


                    }
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

    // source required validation
    if (source.size == 0){
        // error no source
        error(TError.SOURCE_REQUIRED)
        // target required validation
    } else if (target == null){
        // error no target
        error(TError.TARGET_REQUIRED)
    } else
        Transformation(source, target).process()

}


/**
 * Print help
 */
fun help(){
    println("FXML Transform")
    println("Options:")
    println("-s <source fxml file(s) and / or directory(ies) with fxml file(s)>")
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