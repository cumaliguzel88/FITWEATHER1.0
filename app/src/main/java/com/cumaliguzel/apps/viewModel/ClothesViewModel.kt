package com.cumaliguzel.apps.viewModel

import androidx.lifecycle.ViewModel
import com.cumaliguzel.apps.api.WeatherModel
import com.cumaliguzel.apps.data.Clothes
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ClothesViewModel : ViewModel() {

    private var _clothesList = MutableStateFlow<List<Clothes>>(emptyList())
    val clothesList = _clothesList.asStateFlow()

    var weatherData: WeatherModel? = null

    // Cinsiyet seçimi için StateFlow
    private var _gender = MutableStateFlow("male") // Varsayılan olarak "male"
    val gender = _gender.asStateFlow()

    fun setGender(selectedGender: String) {
        _gender.value = selectedGender
        updateWeatherAndFetchClothes() // Cinsiyet değiştiğinde kıyafet listesini yenile
    }

    fun updateWeatherAndFetchClothes() {
        val tempC = weatherData?.current?.temp_c
        val gender = _gender.value
        if (tempC != null) {
            // temp_c'ye ve cinsiyete göre uygun koleksiyonu belirle
            val collectionName = when {
                tempC < 10 -> if (gender == "male") "menwinter" else "womenwinter"
                tempC in 10.0..20.0 -> if (gender == "male") "menspring" else "womenspring"
                tempC > 20 -> if (gender == "male") "mensummer" else "womensummer"
                else -> if (gender == "male") "menwinter" else "womenwinter"
            }
            getClothesList(collectionName)
        } else {
            println("Hava durumu bilgisi yok.")
        }
    }

    private fun getClothesList(collectionName: String) {
        val db = Firebase.firestore
        db.collection(collectionName).addSnapshotListener { value, error ->
            if (error != null) {
                println("Firestore hata: ${error.message}")
                return@addSnapshotListener
            }
            if (value != null) {
                _clothesList.value = value.toObjects()
            }
        }
    }

    fun fetchAndUpdateClothes(weatherModel: WeatherModel) {
        weatherData = weatherModel
        updateWeatherAndFetchClothes()
    }
}
