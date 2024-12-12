package com.cumaliguzel.apps.viewModel

import androidx.lifecycle.ViewModel
import com.cumaliguzel.apps.data.Clothes
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BestClothesViewModel : ViewModel() {

    private val _bestClothesList = MutableStateFlow<List<Clothes>>(emptyList())
    val bestClothesList = _bestClothesList.asStateFlow()

    private var _selectedGender = MutableStateFlow("male")
    val selectedGender = _selectedGender.asStateFlow()

    fun setGender(gender: String) {
        _selectedGender.value = gender
        fetchBestClothes()
    }

    private fun fetchBestClothes() {
        val collectionName = if (_selectedGender.value == "male") "menbest" else "womenbest"
        val db = Firebase.firestore
        db.collection(collectionName).get().addOnSuccessListener { snapshot ->
            val clothesList = snapshot.toObjects<Clothes>()
            _bestClothesList.value = clothesList
        }.addOnFailureListener { exception ->
            println("Error fetching best clothes: ${exception.message}")
        }
    }

    init {
        fetchBestClothes() // Default fetch on initialization
    }
}
