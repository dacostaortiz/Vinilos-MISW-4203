package com.app.vinilos_misw4203.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.network.NetworkServiceAdapter

class PerformerDetailRepository(val application: Application) {

    companion object {
        private const val TAG = "PerformerDetailRepo"
    }

    suspend fun refreshData(id: Int): Performer {
        Log.d(TAG, "Iniciando carga de artista con ID: $id")
        return try {
            val performer = NetworkServiceAdapter
                .getInstance(application)
                .getPerformerCoroutine(id)
            Log.d(TAG, "Artista recibido: ${performer.name} (ID: ${performer.performerId})")
            performer
        } catch (error: VolleyError) {
            Log.e(TAG, "Error al obtener artista", error)
            throw error
        }
    }
}
