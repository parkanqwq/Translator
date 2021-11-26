package com.kalabukhov.app.translator

import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    text: String
){
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

fun View.hideKeyboard(inputMethodManager: InputMethodManager) {
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}