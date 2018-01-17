package io.github.juanlucode.views

import tornadofx.*
import io.github.juanlucode.styles.Styles
import javafx.geometry.Pos
import io.github.juanlucode.controllers.Intro

class Intro: View() {

    val controller = Intro()

    override val root = vbox {
        prefHeight=622.0
        prefWidth=810.0

        label("Dados de Póker")

        hbox{
            alignment = Pos.CENTER
            imageview{
                id = "dado1ImageView"
                image = resources.image("poker1.png")
            }
            imageview{
                id = "dado2ImageView"
                image = resources.image("poker2.png")
            }
            imageview{
                id = "dado3ImageView"
                image = resources.image("poker3.png")
            }
            imageview{
                id = "dado4ImageView"
                image = resources.image("poker4.png")
            }
            imageview{
                id = "dado5ImageView"
                image = resources.image("poker5.png")
            }
        }

        label("@juanlucode")

        hbox{
            alignment = Pos.CENTER
            imageview{
                id = "dado6ImageView"
                image = resources.image("poker6.png")
            }
            imageview{
                id = "dado7ImageView"
                image = resources.image("poker6.png")
            }
            imageview{
                id = "dado8ImageView"
                image = resources.image("poker6.png")
            }
            imageview{
                id = "dado9ImageView"
                image = resources.image("poker6.png")
            }
            imageview{
                id = "dado10ImageView"
                image = resources.image("poker6.png")
            }
        }

        button("Go!"){
            id = "goButton"
            action {
                controller.goButton_click()
            }
        }

    }

    init {
        root.addClass(Styles.introClass)
        root.children.addClass(Styles.introClass)
    }
}