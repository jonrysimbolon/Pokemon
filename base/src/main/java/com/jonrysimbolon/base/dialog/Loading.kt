package com.jonrysimbolon.base.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout

abstract class Loading (
    protected var dialog: Dialog?,
    private val constraintLayout: ConstraintLayout?,
    private val layout: Int
){

    open fun init() {
        val dialogView = LayoutInflater.from(dialog?.context).inflate(layout, constraintLayout)
        dialog?.setContentView(dialogView)
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(1f)
            setFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND
            )
        }
        dialog?.setCancelable(false)
    }

    open fun show(show: Boolean){
        dialog.let {
            if (show) {
                it?.show()
            } else {
                it?.dismiss()
                dialog = null
            }
        }
    }
}