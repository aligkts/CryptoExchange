package com.aligkts.cryptoexchange.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.extension.inflate
import com.aligkts.cryptoexchange.extension.observeNonNull

/**
 * Created by Ali Göktaş on 11.08.2020
 */
abstract class BaseFragment <T: ViewDataBinding, VM: BaseViewModel> : Fragment(), ErrorHandler  {

    protected lateinit var binding: T
    protected lateinit var viewModel: VM

    private var progressDialog: ProgressDialog? = null

    abstract fun getFragmentView() : Int
    abstract fun getViewModel() : Class<VM>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container.inflate(getFragmentView(), false)
        viewModel = ViewModelProvider(this).get(getViewModel())
        binding.apply {
            lifecycleOwner = this@BaseFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.lifecycle.addObserver(viewModel)
        viewModel.contentLoading.observeNonNull(viewLifecycleOwner) {
            if (it) {
                addLoadingView()
            } else {
                removeLoadingView()
            }
        }
        viewModel.errorHandler = this
    }

    override fun onDestroy() {
        super.onDestroy()
        this.lifecycle.removeObserver(viewModel)
    }

    protected open fun addLoadingView() {
        context?.let { c ->
            progressDialog?.dismiss()
            progressDialog = ProgressDialog.show(c, null, getString(R.string.please_wait))
        }
    }

    protected open fun removeLoadingView() {
        progressDialog?.dismiss()
    }

    override fun handleError(message: String) {
        activity?.let { activity ->
            AlertDialog.Builder(activity)
                .setTitle(R.string.error_dialog_title)
                .setMessage(message)
                .setPositiveButton(
                    R.string.done
                ) { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }
                .show()
        }
    }
}