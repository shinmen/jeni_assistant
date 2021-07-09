package fr.julocorp.jenisassistant.utils

import androidx.lifecycle.LiveData
import com.google.common.truth.Truth.assertThat
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}

inline fun <reified T> captor() = ArgumentCaptor.forClass(T::class.java)
