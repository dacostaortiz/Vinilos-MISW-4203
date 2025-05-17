package com.app.vinilos_misw4203.repositories

import android.app.Application
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.network.NetworkServiceAdapter
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PerformerRepository(val application: Application) {
    suspend fun refreshData(): List<Performer> = suspendCoroutine { continuation ->
        NetworkServiceAdapter.getInstance(application).getPerformers(
            { performers -> continuation.resume(performers) },
            { error -> continuation.resumeWithException(error) }
        )
    }
}