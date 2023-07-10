package com.example.recipeapp.utils

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class VolleyRequests {
    private lateinit var listener: VolleyRequestsListener

    interface VolleyRequestsListener {
        fun onDataLoaded(jsonObject: JSONObject)
        fun onError(e: Exception)
    }

    fun setVolleyRequest(listener: VolleyRequestsListener) {
        this.listener = listener
    }

        fun makeGetRequest(mContext: Context, url: String) {
            val queue = Volley.newRequestQueue(mContext)
            val jsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener {
                    listener.onDataLoaded(it)
                }, Response.ErrorListener {
                    listener.onError(it)
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers: HashMap<String, String> = HashMap()
                        headers["Content-type"] = "application/json"
                        headers["key"] = "1"
                        return headers
                    }
                }
            queue.add(jsonObjectRequest)
        }
}