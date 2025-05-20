package com.app.vinilos_misw4203.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.network.NetworkServiceAdapter
import com.app.vinilos_misw4203.database.AppDatabase
import com.app.vinilos_misw4203.database.entities.PerformerEntity

class PerformerRepository(val application: Application) {
    private val networkService = NetworkServiceAdapter.getInstance(application)
    private val performerDao = AppDatabase.getDatabase(application).performerDao()
    private val CACHE_TIMEOUT = 15 * 60 * 1000L // 15 minutes cache validity

    companion object {
        private const val TAG = "PerformerRepository"
    }

    suspend fun refreshData(): List<Performer> {
        Log.d(TAG, "Revisando caché de artistas...")

        val currentTime = System.currentTimeMillis()
        val cacheTime = currentTime - CACHE_TIMEOUT
        val cachedPerformers = performerDao.getFreshPerformers(cacheTime)

        if (cachedPerformers.isNotEmpty()) {
            Log.d(TAG, "Usando datos en cache - se encontraron ${cachedPerformers.size} artistas")
            return cachedPerformers.map { it.toPerformer() }
        }

        return try {
            Log.d(TAG, "Iniciando carga de artistas desde la red con corrutinas...")
            val networkPerformers = networkService.getPerformersCoroutine()
            performerDao.insertAll(networkPerformers.map { PerformerEntity.fromPerformer(it) })
            Log.d(TAG, "Artistas recibidos correctamente. Total: ${networkPerformers.size}")

            networkPerformers
        } catch (error: VolleyError) {
            Log.e(TAG, "Error de red al obtener los artistas", error)
            val allCachedPerformers = performerDao.getAllPerformers()
            if (allCachedPerformers.isNotEmpty()) {
                Log.d(TAG, "Usando datos en caché - se encontraron ${allCachedPerformers.size} artistas")
                return allCachedPerformers.map { it.toPerformer() }
            }
            throw error
        }
    }

    suspend fun getPerformer(id: Int): Performer {
        Log.d(TAG, "Buscando artista con ID $id en caché...")

        val cachedPerformer = performerDao.getPerformerById(id)
        if (cachedPerformer != null) {
            Log.d(TAG, "Artista encontrado en caché")
            return cachedPerformer.toPerformer()
        }

        Log.d(TAG, "Buscando artista con ID $id en la red...")
        val performer = networkService.getPerformerCoroutine(id)
        performerDao.insertPerformer(PerformerEntity.fromPerformer(performer))
        return performer
    }
}