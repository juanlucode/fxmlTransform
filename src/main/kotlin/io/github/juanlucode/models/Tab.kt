package io.github.juanlucode.models

// class for tabulation control

data class Tab(var tabString: String = "\t" ,var tabLevel: Int = 0) {

    fun inc() = tabLevel++

    fun dec() {
        if (tabLevel > 0) tabLevel--
    }

    fun format(text: String) = "${tabString.repeat(tabLevel)}".plus(text)
}