package com.cumaliguzel.apps.viewModel
import androidx.lifecycle.ViewModel
import com.cumaliguzel.apps.api.WeatherModel
import com.cumaliguzel.apps.data.Clothes
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ClothesViewModel : ViewModel()  {
    //data sınıfından bir adet boş liste oluşturdu
    private var _clothesList = MutableStateFlow<List<Clothes>>(emptyList())
    //şimdi de private özelliği devere dışı kaldı neden yaptı bilmiyorum
    var clothesList = _clothesList.asStateFlow()
    var weatherData: WeatherModel? = null


    init {
        getClothesList()
    }
    fun getClothesList(){


        // crate db
        val db = Firebase.firestore
        db.collection("mensummer").addSnapshotListener{
                value,error ->
            if(error != null){
                return@addSnapshotListener
            }
            if(value != null){
                _clothesList.value = value.toObjects()
            }
        }


    }






}