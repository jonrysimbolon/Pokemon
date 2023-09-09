package com.jonrysimbolon.base.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout

abstract class Loading(
    context: Context
) {
    protected var dialog: Dialog = Dialog(context)
    private val constraintLayout: ConstraintLayout = ConstraintLayout(context)
    abstract val layout: Int

    open fun init() {
        val dialogView = LayoutInflater.from(dialog.context).inflate(layout, constraintLayout)
        dialog.apply {
            setContentView(dialogView)
            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setDimAmount(1f)
                setFlags(
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND
                )
            }
            setCancelable(false)
        }
    }

    open fun show(isShow: Boolean) {
        dialog.apply {
            if (isShow)
                show()
            else
                dismiss()
        }
    }
}