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

    fun refreshData(callback: (List<Performer>) -> Unit, onError: (VolleyError) -> Unit) {
        Log.d(TAG, "Iniciando carga de artistas desde NetworkServiceAdapter...")

        NetworkServiceAdapter.getInstance(application).getPerformers({ performers ->
            Log.d(TAG, "Artistas recibidos correctamente. Total: ${performers.size}")
            // Print details for each performer
            performers.forEach {
                Log.d(TAG, "Artista: ${it.name} (Tipo: ${it.performerType})")
            }
            callback(performers)
        }, { error ->
            Log.e(TAG, "Error al obtener los artistas", error)
            onError(error)
        })
    }
}