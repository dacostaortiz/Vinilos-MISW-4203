package com.app.vinilos_misw4203.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.vinilos_misw4203.models.Album
import com.app.vinilos_misw4203.models.Performer
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),
                                        name = item.getString("name"), 
                                        coverUrl = item.getString("cover"), 
                                        recordLabel = item.getString("recordLabel"), 
                                        releaseDate = item.getString("releaseDate"), 
                                        genre = item.getString("genre"), 
                                        description = item.getString("description")))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    fun getPerformers(onComplete: (resp: List<Performer>) -> Unit, onError: (error: VolleyError) -> Unit){
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Performer>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Performer(performerId = item.getInt("id"),
                                        name = item.getString("name"),
                                        image = item.getString("image"),
                                        description = item.getString("description"),
                                        performerType = "musician", //item.getString("type"),
                                        birthDate = item.optString("birthDate", null)))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    fun getPerformer(id: Int, onComplete: (resp: Performer) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(getRequest("musicians/$id",
            Response.Listener<String> { response ->
                val item = JSONObject(response)
                val performer = Performer(performerId = item.getInt("id"),
                    name = item.getString("name"),
                    image = item.getString("image"),
                    description = item.getString("description"),
                    performerType = "musician",
                    birthDate = item.optString("birthDate", null))
                onComplete(performer)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    suspend fun getAlbumsCoroutine(): List<Album> = suspendCoroutine { continuation ->
        getAlbums(
            onComplete = { albums -> continuation.resume(albums) },
            onError = { error -> continuation.resumeWithException(error) }
        )
    }

    suspend fun getPerformersCoroutine(): List<Performer> = suspendCoroutine { continuation ->
        getPerformers(
            onComplete = { performers -> continuation.resume(performers) },
            onError = { error -> continuation.resumeWithException(error) }
        )
    }

    suspend fun getPerformerCoroutine(id: Int): Performer = suspendCoroutine { continuation ->
        getPerformer(
            id,
            onComplete = { performer -> continuation.resume(performer) },
            onError = { error -> continuation.resumeWithException(error) }
        )
    }

    private fun getRequest(path: String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }
}