package com.jonrysimbolon.base.dialog.ui

import android.content.Context
import com.jonrysimbolon.base.R
import com.jonrysimbolon.base.dialog.Loading

class LoadingImpl(
    context: Context,
    override val layout: Int = R.layout.dialog_loading
) : Loading(
    context
)