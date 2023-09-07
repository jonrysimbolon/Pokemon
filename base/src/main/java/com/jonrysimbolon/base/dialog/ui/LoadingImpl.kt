package com.jonrysimbolon.base.dialog.ui

import android.app.Dialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.jonrysimbolon.base.R
import com.jonrysimbolon.base.dialog.Loading

class LoadingImpl(
    dialog: Dialog,
    constraintLayout: ConstraintLayout,
    layout: Int = R.layout.dialog_loading
) : Loading(
    dialog,
    constraintLayout,
    layout
) {

    init {
        init()
    }

}