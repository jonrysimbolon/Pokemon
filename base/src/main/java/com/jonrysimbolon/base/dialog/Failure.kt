package com.jonrysimbolon.base.dialog

import android.content.Context
import android.widget.Button

abstract class Failure(
    context: Context,
) : Loading(
    context
) {

    abstract var reloadButton: Button?
    abstract fun setDescription(description: String)
}