package com.rediz.pokemon.presentation.ui.pokelist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rediz.pokemon.common.MutableEventFlow
import com.rediz.pokemon.common.asEventFlow
import com.rediz.pokemon.domain.model.PokeDetail
import com.rediz.pokemon.domain.repository.Repository
import com.rediz.pokemon.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository,
) : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun getList() {
        viewModelScope.launch {
            repository.getList().cachedIn(viewModelScope).collectLatest {
                _eventFlow.emit(Event.Update(it))
            }
        }
    }

    sealed interface Event {
        data class Update(val data: PagingData<PokeDetail>) : Event
    }
}