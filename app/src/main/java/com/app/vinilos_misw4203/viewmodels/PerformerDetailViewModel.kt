package com.app.vinilos_misw4203.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.repositories.PerformerDetailRepository
import kotlinx.coroutines.launch

class PerformerDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PerformerDetailRepository(application)

    private val _performer = MutableLiveData<Performer>()
    val performer: LiveData<Performer> = _performer

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> = _isNetworkErrorShown

    fun refreshPerformerDetail(id: Int) {
        viewModelScope.launch {
            try {
                val performer = repository.refreshData(id)
                _performer.value = performer
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (e: Exception) {
                _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct PerformerDetailViewModel")
        }
    }
}