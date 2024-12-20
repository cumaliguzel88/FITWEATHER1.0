package com.cumaliguzel.apps.data

data class Comment(
    val commentId: Int = 0,         // Yorumun benzersiz ID'si
    val clothesId: Int = 0,         // Hangi kıyafete ait olduğunu belirtir
    val userId: String = "",        // Kullanıcının kimliği (isteğe bağlı)
    val username: String = "Anonim",// Kullanıcı adı
    val content: String = ""        // Yorumun içeriği
)
