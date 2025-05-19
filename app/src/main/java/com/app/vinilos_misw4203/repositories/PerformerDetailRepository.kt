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

    fun refreshData(
        id: Int,
        onSuccess: (Performer) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        Log.d(TAG, "Iniciando carga de artista con ID: $id")
        NetworkServiceAdapter.getInstance(application)
            .getPerformer(id,
                { performer ->
                    Log.d(TAG, "Artista recibido: ${performer.name} (ID: ${performer.performerId})")
                    onSuccess(performer)
                },
                { error ->
                    Log.e(TAG, "Error al obtener artista", error)
                    onError(error)
                }
            )
    }
}
