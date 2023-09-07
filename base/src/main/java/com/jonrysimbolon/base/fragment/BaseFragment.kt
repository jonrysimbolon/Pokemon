package com.jonrysimbolon.base.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.jonrysimbolon.base.R
import com.jonrysimbolon.base.dialog.Failure
import com.jonrysimbolon.base.dialog.Loading
import com.jonrysimbolon.base.dialog.ui.FailureImpl
import com.jonrysimbolon.base.dialog.ui.LoadingImpl
import com.jonrysimbolon.base.viewmodel.BaseViewModel

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<T : ViewBinding, VM : BaseViewModel>(
    private val inflateMethod: Inflate<T>
) : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected abstract val baseViewModel: VM

    private var loadingDialog: Loading? = null

    private var failureDialog: Failure? = null

    private fun baseError(
        error: String,
        dismiss: Boolean,
        action: (Failure) -> Unit
    ){
        loadingDialog?.show(false)
        failureDialog?.apply {
            show(true)
            setDescription(error)
            if(dismiss){
                reloadButton?.text = getString(R.string.dismiss)
            }
            reloadButton?.setOnClickListener {
                action(this)
            }
        }
    }

    protected fun showError(
        error: String,
        reAction: () -> Unit
    ) {

        baseError(error,false){ failure ->
            reAction()
            failure.show(false)
        }
    }

    protected fun showErrorDismiss(
        error: String
    ) {
        baseError(error, true){ failure ->
            failure.show(false)
        }
    }

    protected fun showLoading(){
        loadingDialog?.show(true)
    }

    protected fun hideLoading(){
        loadingDialog?.show(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected open fun setUpUi(savedInstanceState: Bundle?) = Unit
    protected open fun setUpVm() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingImpl(
            Dialog(requireContext()),
            ConstraintLayout(requireContext())
        )
        failureDialog = FailureImpl(
            Dialog(requireContext()),
            ConstraintLayout(requireContext())
        )
        setUpUi(savedInstanceState)
        setUpVm()
    }
}