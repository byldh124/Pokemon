package com.rediz.pokemon.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rediz.pokemon.common.MutableEventFlow
import com.rediz.pokemon.common.asEventFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


open class BaseViewModel : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private var _commonEvent = MutableEventFlow<CommonEvent>()
    val commonEvent = _commonEvent.asEventFlow()

    protected fun fail(message: String, callback: () -> Unit = {}) =
        event(CommonEvent.Fail(message, callback))

    protected fun finish() = event(CommonEvent.Finish)

    protected fun error(throwable: Throwable, callback: () -> Unit = {}) =
        event(CommonEvent.Error(throwable, callback))

    protected fun loading(isLoading: Boolean) = event(CommonEvent.Loading(isLoading))

    protected fun message(message: String, callback: () -> Unit = {}) =
        event(CommonEvent.Message(message, callback))

    protected fun toast(message: String) = event(CommonEvent.Toast(message))

    private fun event(event: CommonEvent) {
        viewModelScope.launch {
            _commonEvent.emit(event)
        }
    }

    sealed interface CommonEvent {
        data class Loading(val isLoading: Boolean) : CommonEvent
        data class Fail(val message: String, val callback: () -> Unit) : CommonEvent
        data class Error(val throwable: Throwable, val callback: () -> Unit) : CommonEvent
        data class Message(val message: String, val callback: () -> Unit) : CommonEvent
        data class Toast(val message: String) : CommonEvent
        data object Finish : CommonEvent
    }
}