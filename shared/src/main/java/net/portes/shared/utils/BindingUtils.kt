package net.portes.shared.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import net.portes.shared.R

/**
 * @author amadeus.portes
 */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_movie_default)
        .fit()
        .centerCrop()
        .into(view)
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}