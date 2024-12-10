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

    // Firestore'dan gelen kıyafet listesini tutan StateFlow
    private var _clothesList = MutableStateFlow<List<Clothes>>(emptyList())
    val clothesList = _clothesList.asStateFlow()

    // Hava durumu verisi (WeatherModel)
    var weatherData: WeatherModel? = null

    /**
     * Hava durumu verisi güncellendiğinde, sıcaklığa göre doğru koleksiyonu seç
     * ve Firestore'dan verileri al.
     */
    fun updateWeatherAndFetchClothes() {
        val tempC = weatherData?.current?.temp_c
        if (tempC != null) {
            // temp_c'ye göre uygun koleksiyonu belirle
            val collectionName = when {
                tempC < 10 -> "menwinter" // 10°C altı için
                tempC in 10.0..20.0 -> "menspring" // 10-20°C arası
                tempC > 20 -> "mensummer" // 20°C üstü
                else -> "menwinter" // Varsayılan koleksiyon
            }
            // Seçilen koleksiyondan verileri al
            getClothesList(collectionName)
        } else {
            println("Hava durumu bilgisi yok.")
        }
    }

    /**
     * Belirtilen koleksiyondan kıyafet listesini getir ve StateFlow'u güncelle.
     */
    private fun getClothesList(collectionName: String) {
        val db = Firebase.firestore
        db.collection(collectionName).addSnapshotListener { value, error ->
            if (error != null) {
                println("Firestore hata: ${error.message}")
                return@addSnapshotListener
            }
            if (value != null) {
                // Firestore'dan gelen verileri listeye çevir ve güncelle
                _clothesList.value = value.toObjects()
            }
        }
    }

    /**
     * Dışarıdan WeatherModel ile hava durumunu güncelle ve kıyafet listesini yenile.
     */
    fun fetchAndUpdateClothes(weatherModel: WeatherModel) {
        weatherData = weatherModel
        updateWeatherAndFetchClothes()
    }
}

