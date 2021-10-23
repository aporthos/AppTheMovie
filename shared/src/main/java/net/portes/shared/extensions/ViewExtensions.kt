package net.portes.shared.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author amadeus.portes
 */
fun <DB : ViewDataBinding> ViewGroup.binding(view: Int) : DB {
    return DataBindingUtil.inflate(
        LayoutInflater.from(context),
        view,
        this,
        false
    )
}