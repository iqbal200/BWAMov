package id.co.project.bwamov.utils

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import id.co.project.bwamov.R

fun Activity.showLoading() {
    val inflater = layoutInflater
    val layout = inflater.inflate(R.layout.show_loading, null)
    val alertDialog: AlertDialog = MyLoading.newInstance(this)
    alertDialog.setView(layout)
    alertDialog.setCancelable(true)
    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.show()
}

fun Activity.dismisLoading() {
    val alertDialog: AlertDialog = MyLoading.newInstance(this)
    alertDialog.dismiss()
}
