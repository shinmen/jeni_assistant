package fr.julocorp.jenisassistant.infrastructure

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Snackbar.error(context: Context): Snackbar = this.apply {
    setAnchorView(R.id.nav_view)
    with(view) {
        setTextColor(ContextCompat.getColor(context, R.color.white))
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
    }
    show()
}

fun Snackbar.success(context: Context): Snackbar = this.apply {
    setAnchorView(R.id.nav_view)
    with(view) {
        setTextColor(ContextCompat.getColor(context, R.color.white))
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
    }
    show()
}

inline fun <reified U> castGeneric(parameter: Any): U = parameter as U

fun List<Caracteristique<*>>.findByLabel(label: String) = this.firstOrNull { it.label == label }