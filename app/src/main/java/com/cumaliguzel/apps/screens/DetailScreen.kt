package com.cumaliguzel.apps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.cumaliguzel.apps.api.NetworkResponse
import com.cumaliguzel.apps.components.CustomTopAppBar
import com.cumaliguzel.apps.data.Clothes
import com.cumaliguzel.apps.data.Comment
import com.cumaliguzel.apps.viewModel.ClothesViewModel
import com.cumaliguzel.apps.viewModel.CommentsViewModel
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    clothesViewModel: ClothesViewModel,
    commentsViewModel: CommentsViewModel,
    clothes: Clothes,
    navController: NavController
) {
    val commentsResult = commentsViewModel.commentsResult.collectAsState()
    var newComment by remember { mutableStateOf("") }



    LaunchedEffect1(clothes.id) {
        commentsViewModel.fetchComments(clothes.id)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Clothes Details",
                onBackClick = { navController.popBackStack() }
            )
        },

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AsyncImage(
                    model = clothes.img,
                    contentDescription = "Clothes Image",
                    modifier = Modifier.size(400.dp)
                )
                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { clothesViewModel.openTopLink(clothes.topLink) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "👕 Top Link")
                    }
                    Button(
                        onClick = { clothesViewModel.openBottomLink(clothes.bottomLink) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "👖 Bottom Link")
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium)

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            OutlinedTextField(
                                value = newComment,
                                onValueChange = { newComment = it },
                                placeholder = { Text("Add your Desicion...",) },
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                shape =  RoundedCornerShape(10.dp),
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            if (newComment.isNotBlank()) {
                                                commentsViewModel.addComment(
                                                    clothesId = clothes.id,
                                                    comment = Comment(
                                                        username = "Anonim",
                                                        content = newComment
                                                    )
                                                )
                                                newComment = ""
                                            }
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Send, // Gönderme için bir ikon
                                            contentDescription = "Send Comment",
                                            tint = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Edit, // İstediğin bir ikon
                                        contentDescription = "Add Comment",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                }

                            )
                        }

                    }
                }

            }

            // Yorumlar durumuna göre gösterim
            when (val result = commentsResult.value) {
                is NetworkResponse.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is NetworkResponse.Error -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = result.message,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                is NetworkResponse.Success -> {
                    items(result.data) { comment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = MaterialTheme.shapes.small,
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = comment.username,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = comment.content,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

