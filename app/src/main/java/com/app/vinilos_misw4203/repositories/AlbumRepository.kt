package com.app.vinilos_misw4203.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.app.vinilos_misw4203.network.NetworkServiceAdapter
import com.app.vinilos_misw4203.models.Album

class AlbumRepository(val application: Application) {

    companion object {
        private const val TAG = "AlbumRepository"
    }

    suspend fun refreshData(): List<Album> {
        Log.d(TAG, "Iniciando carga de álbumes con coroutines...")
        return try {
            val albums = NetworkServiceAdapter.getInstance(application).getAlbumsCoroutine()
            Log.d(TAG, "Álbumes recibidos correctamente. Total: ${albums.size}")
            albums
        } catch (error: VolleyError) {
            Log.e(TAG, "Error al obtener los álbumes", error)
            throw error
        }
    }
}