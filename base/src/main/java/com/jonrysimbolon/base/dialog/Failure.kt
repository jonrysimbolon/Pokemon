package com.jonrysimbolon.base.dialog

import android.app.Dialog
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

abstract class Failure(
    dialog: Dialog,
    constraintLayout: ConstraintLayout?,
    layout: Int
) : Loading(
    dialog,
    constraintLayout,
    layout
) {

    abstract var reloadButton: Button?
    abstract fun setDescription(description: String)
}