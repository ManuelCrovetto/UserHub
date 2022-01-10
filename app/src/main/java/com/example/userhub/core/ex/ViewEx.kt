package com.example.userhub.core.ex

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.userhub.R
import com.google.android.material.snackbar.Snackbar

fun View.lostConnectionSnackBar() {
    val snackBar = Snackbar.make(this, context.getString(R.string.lost_connection_snack_bar_placeholder), Snackbar.LENGTH_INDEFINITE)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(Color.RED)
    snackBar.show()
}

fun View.reconnectedSnackBar() {
    val snackBar = Snackbar.make(this, context.getString(R.string.reconnected_snack_bar_placeholder), Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(Color.GREEN)
    val snackBarTextView: TextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
    snackBarTextView.setTextColor(Color.BLACK)
    snackBar.show()
}