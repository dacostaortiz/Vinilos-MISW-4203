package com.app.vinilos_misw4203.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.app.vinilos_misw4203.network.NetworkServiceAdapter
import com.app.vinilos_misw4203.models.Album
import com.app.vinilos_misw4203.database.AppDatabase
import com.app.vinilos_misw4203.database.entities.AlbumEntity

class AlbumRepository(val application: Application) {
    private val networkService = NetworkServiceAdapter.getInstance(application)
    private val albumDao = AppDatabase.getDatabase(application).albumDao()
    private val CACHE_TIMEOUT = 15 * 60 * 1000L // 15 minutes cache validity

    companion object {
        private const val TAG = "AlbumRepository"
    }

    suspend fun refreshData(): List<Album> {
        Log.d(TAG, "Revisando caché de álbumes...")

        val currentTime = System.currentTimeMillis()
        val cacheTime = currentTime - CACHE_TIMEOUT
        val cachedAlbums = albumDao.getFreshAlbums(cacheTime)

        if (cachedAlbums.isNotEmpty()) {
            Log.d(TAG, "Usando datos en cache - se encontraron ${cachedAlbums.size} albums")
            return cachedAlbums.map { it.toAlbum() }
        }

        return try {
            Log.d(TAG, "Iniciando carga de álbums desde la red con corrutinas...")
            val networkAlbums = networkService.getAlbumsCoroutine()
            albumDao.insertAll(networkAlbums.map { AlbumEntity.fromAlbum(it) })
            Log.d(TAG, "Álbums recibidos correctamente. Total: ${networkAlbums.size}")

            networkAlbums
        } catch (error: VolleyError) {
            Log.e(TAG, "Error de red al obtener los álbums", error)
            val allCachedAlbums = albumDao.getAllAlbums()
            if (allCachedAlbums.isNotEmpty()) {
                Log.d(TAG, "Usando datos en caché - se encontraron ${allCachedAlbums.size} albums")
                return allCachedAlbums.map { it.toAlbum() }
            }
            throw error
        }
    }
}