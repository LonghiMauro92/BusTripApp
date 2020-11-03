package com.example.baseproyect.ui

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.baseproyect.R
import com.example.baseproyect.ui.StringUtils.EMPTY_STRING

fun invokeAlertDialog(
    activity: Activity,
    title: String = EMPTY_STRING,
    message: String,
    positiveButtonS: String="",
    negativeButtonS: String="",
    positiveButton: (() -> Unit)? = null,
    negativeButton: (() -> Unit)? = null
) {
    val dialog = AlertDialog.Builder(activity, R.style.BaseAlertDialogTheme)
        .setPositiveButton(positiveButtonS) { dialog, _ ->
            dialog.dismiss()
            positiveButton?.invoke()
        }
        .setMessage(message)
        .setCancelable(false)

    negativeButton?.let {
        dialog.setNegativeButton(negativeButtonS) { dialog, _ ->
            dialog.dismiss()
            it.invoke()
        }
    }

    if (title.isNotEmpty()) {
        dialog.setTitle(title)
    }

    dialog.create().show()
}