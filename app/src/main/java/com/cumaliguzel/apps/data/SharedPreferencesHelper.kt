package com.cumaliguzel.apps.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val gson = Gson()

    /**
     * Saves a list of favorite IDs to SharedPreferences.
     * @param favorites List of favorite IDs.
     */
    fun saveFavorites(favorites: List<Int>) {
        val json = gson.toJson(favorites)
        sharedPreferences.edit()
            .putString("favorites_list", json)
            .apply()
    }

    /**
     * Retrieves a list of favorite IDs from SharedPreferences.
     * @return List of favorite IDs.
     */
    fun getFavorites(): List<Int> {
        val json = sharedPreferences.getString("favorites_list", "[]") ?: "[]"
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

}
