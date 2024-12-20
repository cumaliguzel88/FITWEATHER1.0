package com.cumaliguzel.apps.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cumaliguzel.apps.api.NetworkResponse
import com.cumaliguzel.apps.data.Comment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommentsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Yorumların durumu (Loading, Success, Error)
    private val _commentsResult = MutableStateFlow<NetworkResponse<List<Comment>>>(NetworkResponse.Loading)
    val commentsResult: StateFlow<NetworkResponse<List<Comment>>> = _commentsResult

    // Yorumları Firestore'dan Çekme
    fun fetchComments(clothesId: Int) {
        _commentsResult.value = NetworkResponse.Loading // Yükleniyor durumu
        db.collection("comments")
            .whereEqualTo("clothesId", clothesId) // İlgili kıyafete ait yorumları getir
            .orderBy("commentId", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _commentsResult.value = NetworkResponse.Error("Error fetching comments: ${error.message}")
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val commentList = snapshot.toObjects(Comment::class.java)
                    _commentsResult.value = NetworkResponse.Success(commentList) // Başarıyla yüklendi
                } else {
                    _commentsResult.value = NetworkResponse.Success(emptyList()) // Boş liste
                }
            }
    }

    // Yeni Yorum Ekleme
    fun addComment(clothesId: Int, comment: Comment) {
        val documentRef = db.collection("comments").document()
        val commentWithId = comment.copy(
            commentId = documentRef.id.hashCode(), // Benzersiz yorum ID'si
            clothesId = clothesId // Kıyafet ID'sine bağlanıyor
        )
        _commentsResult.value = NetworkResponse.Loading // Yükleniyor durumuna geç

        documentRef.set(commentWithId)
            .addOnSuccessListener {
                println("Comment added successfully!")
                fetchComments(clothesId) // Yorum eklendiğinde güncel yorumları getir
            }
            .addOnFailureListener { error ->
                println("Error adding comment: ${error.message}")
                _commentsResult.value = NetworkResponse.Error("Error adding comment: ${error.message}")
            }
    }


}
