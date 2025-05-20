package com.app.vinilos_misw4203.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.network.NetworkServiceAdapter

class PerformerRepository(val application: Application) {

    companion object {
        private const val TAG = "PerformerRepository"
    }

    suspend fun refreshData(): List<Performer> {
        Log.d(TAG, "Iniciando carga de artistas con coroutines...")
        return try {
            val performers = NetworkServiceAdapter.getInstance(application).getPerformersCoroutine()
            Log.d(TAG, "Artistas recibidos correctamente. Total: ${performers.size}")
            performers
        } catch (error: VolleyError) {
            Log.e(TAG, "Error al obtener los artistas", error)
            throw error
        }
    }
}