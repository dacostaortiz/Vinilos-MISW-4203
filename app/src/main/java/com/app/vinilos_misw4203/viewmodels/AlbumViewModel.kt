package com.app.vinilos_misw4203.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.app.vinilos_misw4203.repositories.AlbumRepository
import com.app.vinilos_misw4203.models.Album
import kotlinx.coroutines.launch

class AlbumViewModel(application: Application) :  AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(application)

    private val _albums = MutableLiveData<List<Album>>()

    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch {
            try {
                val albumList = albumsRepository.refreshData()
                _albums.value = albumList
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

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
