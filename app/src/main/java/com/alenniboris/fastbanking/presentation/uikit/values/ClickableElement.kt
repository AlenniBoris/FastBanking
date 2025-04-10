package com.alenniboris.fastbanking.presentation.uikit.values

data class ClickableElement(
    val text: String,
    val onClick: () -> Unit
) {
    override fun equals(other: Any?): Boolean {
        (other as? ClickableElement)?.let {
            return other.text == this.text
        }
        return false
    }
}