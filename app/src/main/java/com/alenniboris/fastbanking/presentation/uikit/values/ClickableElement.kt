package com.alenniboris.fastbanking.presentation.uikit.values

data class ClickableElement(
    val text: String,
    val onClick: () -> Unit
) {

    override fun equals(other: Any?): Boolean {
        return (other as? ClickableElement)?.let {
            this.text == other.text
        } ?: false
    }
}