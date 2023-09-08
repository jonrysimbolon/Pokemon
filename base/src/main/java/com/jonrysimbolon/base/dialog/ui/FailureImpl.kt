package com.jonrysimbolon.base.dialog.ui

import android.content.Context
import android.widget.Button
import com.google.android.material.textview.MaterialTextView
import com.jonrysimbolon.base.R
import com.jonrysimbolon.base.dialog.Failure

class FailureImpl(
    context: Context,
    override val layout: Int = R.layout.dialog_error
) : Failure(
    context
) {

    override var reloadButton: Button? = null

    override fun setDescription(description: String) {
        val descriptionTextView = dialog.findViewById<MaterialTextView>(R.id.descFailed)
        descriptionTextView?.text = description
    }

    override fun init() {
        super.init()
        val reloadBtn = dialog.findViewById<Button>(R.id.reloadBtn)
        if (reloadBtn != null) {
            reloadButton = reloadBtn
        }
    }
}