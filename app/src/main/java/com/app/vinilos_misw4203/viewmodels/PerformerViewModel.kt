package com.app.vinilos_misw4203.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.volley.VolleyError
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.repositories.PerformerRepository

class PerformerViewModel(application: Application) : AndroidViewModel(application) {

    private val _performers = MutableLiveData<List<Performer>>()
    val performers: LiveData<List<Performer>> get() = _performers

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    private val performerRepository = PerformerRepository(application)

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        performerRepository.refreshData({ performers ->
            _performers.postValue(performers)
            _eventNetworkError.postValue(false)
            _isNetworkErrorShown.postValue(false)
        }, { error ->
            _eventNetworkError.postValue(true)
            Log.e("PerformerViewModel", "Error fetching performers", error)
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}