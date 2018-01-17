package io.github.juanlucode.views

import io.github.juanlucode.styles.Styles
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import tornadofx.*


class IntroFromFxml: View() {

    override val root : VBox by fxml()
    val titleLabel: Label by fxid()
    val authorLabel: Label by fxid()
    val goButton: Button by fxid()
    val dado1ImageView: ImageView by fxid()
    val dado2ImageView: ImageView by fxid()
    val dado3ImageView: ImageView by fxid()
    val dado4ImageView: ImageView by fxid()
    val dado5ImageView: ImageView by fxid()
    val dado6ImageView: ImageView by fxid()
    val dado7ImageView: ImageView by fxid()
    val dado8ImageView: ImageView by fxid()
    val dado9ImageView: ImageView by fxid()
    val dado10ImageView: ImageView by fxid()


    init {
        root.addClass(Styles.introClass)
        root.children.addClass(Styles.introClass)
        setImageView(dado1ImageView, resources.image("poker1.png"))
        setImageView(dado2ImageView, resources.image("poker2.png"))
        setImageView(dado3ImageView, resources.image("poker3.png"))
        setImageView(dado4ImageView, resources.image("poker4.png"))
        setImageView(dado5ImageView, resources.image("poker5.png"))
        setImageView(dado6ImageView, resources.image("poker6.png"))
        setImageView(dado7ImageView, resources.image("poker6.png"))
        setImageView(dado8ImageView, resources.image("poker5.png"))
        setImageView(dado9ImageView, resources.image("poker4.png"))
        setImageView(dado10ImageView, resources.image("poker3.png"))

    }

}