package com.cumaliguzel.apps.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cumaliguzel.apps.api.WeatherModel
import com.cumaliguzel.apps.data.Clothes
import com.cumaliguzel.apps.data.SharedPreferencesHelper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClothesViewModel(private val context: Context) : ViewModel() {

    private val sharedPreferencesHelper = SharedPreferencesHelper(context)

    // Clothes list state
    private var _clothesList = MutableStateFlow<List<Clothes>>(emptyList())
    val clothesList = _clothesList.asStateFlow()

    // Favorites state (id tabanlı favoriler)
    private var _favorites = MutableStateFlow<List<Int>>(sharedPreferencesHelper.getFavorites())
    val favorites = _favorites.asStateFlow()

    // Weather and gender-related data
    var weatherData: WeatherModel? = null
    private var _gender = MutableStateFlow("male") // Default gender
    val gender = _gender.asStateFlow()

    /**
     * Set the gender and update the clothes list accordingly.
     */
    fun setGender(selectedGender: String) {
        if (_gender.value != selectedGender) {
            _gender.value = selectedGender
            updateWeatherAndFetchClothes()
        }
    }

    /**
     * Updates the clothes list based on weather and gender.
     * Ensures `isFavorite` states are preserved.
     */
    fun updateWeatherAndFetchClothes() {
        val tempC = weatherData?.current?.temp_c
        val gender = _gender.value
        if (tempC != null) {
            val collectionName = when {
                tempC < 16 -> if (gender == "male") "menwinter" else "womenwinter"
                tempC in 16.0..22.0 -> if (gender == "male") "menspring" else "womenspring"
                tempC > 22 -> if (gender == "male") "mensummer" else "womensummer"
                else -> if (gender == "male") "menwinter" else "womenwinter"
            }
            getClothesList(collectionName)
        } else {
            println("Hava durumu bilgisi yok.")
        }
    }


    /**
     * Fetches the clothes list from Firebase Firestore and ensures `isFavorite` states are preserved.
     */
    private fun getClothesList(collectionName: String) {
        val db = Firebase.firestore
        db.collection(collectionName).addSnapshotListener { value, error ->
            if (error != null) {
                println("Firestore hata: ${error.message}")
                return@addSnapshotListener
            }
            if (value != null) {
                val newClothesList = value.toObjects<Clothes>().map { clothes ->
                    clothes.copy(
                        isFavorite = _favorites.value.contains(clothes.id)
                    )
                }
                _clothesList.value = newClothesList
            }
        }
    }

    /**
     * Toggles the favorite status of a Clothes item.
     * Updates both the `favorites` state and the `clothesList`.
     */
    fun toggleFavorite(clothes: Clothes) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentFavorites = _favorites.value.toMutableList()

            if (currentFavorites.contains(clothes.id)) {
                currentFavorites.remove(clothes.id)
            } else {
                currentFavorites.add(clothes.id)
            }

            _favorites.value = currentFavorites
            sharedPreferencesHelper.saveFavorites(currentFavorites)
            updateClothesList(_clothesList.value)
        }
    }

    /**
     * Updates the clothes list and ensures `isFavorite` states are synchronized.
     */
    fun updateClothesList(newClothes: List<Clothes>) {
        _clothesList.value = newClothes.map { clothes ->
            clothes.copy(isFavorite = _favorites.value.contains(clothes.id))
        }
    }

    /**
     * Updates weather data and refreshes the clothes list.
     */
    fun fetchAndUpdateClothes(weatherModel: WeatherModel) {
        weatherData = weatherModel
        updateWeatherAndFetchClothes()
    }

    /**
     * Opens the top link in a browser.
     */
    fun openTopLink(topLink: String) {
        if (topLink.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(topLink))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    /**
     * Opens the bottom link in a browser.
     */
    fun openBottomLink(bottomLink: String) {
        if (bottomLink.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bottomLink))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
    fun getClothesById(clothesId: Int): Clothes? {
        return _clothesList.value.find { it.id == clothesId }
    }


}
