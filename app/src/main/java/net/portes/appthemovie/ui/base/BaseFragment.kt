package net.portes.appthemovie.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import net.portes.shared.ui.widget.LoaderDialog

/**
 * @author amadeus.portes
 */
abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {
    private var loaderDialog: LoaderDialog? = null

    private lateinit var binding: DB
    protected fun dataBinding(): DB = binding
    protected abstract fun initializeView()
    protected abstract fun initObservers()

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            loaderDialog = LoaderDialog(it)
        }

        initializeView()
        initObservers()
    }

    protected fun showLoader() {
        loaderDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    protected fun hideLoader() {
        loaderDialog?.dismiss()
    }

}