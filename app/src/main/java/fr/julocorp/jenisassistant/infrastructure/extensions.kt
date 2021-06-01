package fr.julocorp.jenisassistant.infrastructure

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import fr.julocorp.jenisassistant.R

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Snackbar.error(context: Context): Snackbar = this.apply {
    setAnchorView(R.id.nav_view)
    with(view) {
        setTextColor(ContextCompat.getColor(context, R.color.white))
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
    }
}

fun Snackbar.success(context: Context): Snackbar = this.apply {
    setAnchorView(R.id.nav_view)
    with(view) {
        setTextColor(ContextCompat.getColor(context, R.color.white))
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
    }
}
