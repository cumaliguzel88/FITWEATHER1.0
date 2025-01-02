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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.cumaliguzel.apps.R
import com.cumaliguzel.apps.api.NetworkResponse
import com.cumaliguzel.apps.components.CustomTopAppBar
import com.cumaliguzel.apps.data.Clothes
import com.cumaliguzel.apps.data.Comment
import com.cumaliguzel.apps.data.WindowSize
import com.cumaliguzel.apps.data.WindowType
import com.cumaliguzel.apps.data.rememberWindowSize
import com.cumaliguzel.apps.viewModel.ClothesViewModel
import com.cumaliguzel.apps.viewModel.CommentsViewModel
import com.cumaliguzel.apps.animations.LottieAnimationComposable
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect1

@Composable
fun DetailScreen(
    clothesViewModel: ClothesViewModel,
    commentsViewModel: CommentsViewModel,
    clothes: Clothes,
    navController: NavController
) {
    val windowSize = rememberWindowSize()
    val commentsResult = commentsViewModel.commentsResult.collectAsState()
    var newComment by remember { mutableStateOf("") }

    LaunchedEffect1(clothes.id) {
        commentsViewModel.fetchComments(clothes.id)
    }

    // Dinamik boyut ayarları
    val imageSize = when (windowSize.width) {
        WindowType.Compact -> 200.dp
        WindowType.Medium -> 300.dp
        WindowType.Expanded -> 400.dp
    }

    val buttonFontSize = when (windowSize.width) {
        WindowType.Compact -> 12.sp
        WindowType.Medium -> 14.sp
        WindowType.Expanded -> 16.sp
    }

    val verticalSpacing = when (windowSize.height) {
        WindowType.Compact -> 8.dp
        WindowType.Medium -> 12.dp
        WindowType.Expanded -> 16.dp
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.detail_page_title),
                onBackClick = { navController.popBackStack() }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(verticalSpacing)
        ) {
            item {
                AsyncImage(
                    model = clothes.img,
                    contentDescription = "Clothes Image",
                    modifier = Modifier.size(400.dp)
                )
                Spacer(modifier = Modifier.height(verticalSpacing))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { clothesViewModel.openTopLink(clothes.topLink) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = stringResource(R.string.detail_page_top_link_label),
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = buttonFontSize
                        )
                    }
                    Button(
                        onClick = { clothesViewModel.openBottomLink(clothes.bottomLink) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = stringResource(R.string.detail_page_bottom_link_label),
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = buttonFontSize
                        )
                    }
                }
                Spacer(modifier = Modifier.height(verticalSpacing))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            value = newComment,
                            onValueChange = { newComment = it },
                            placeholder = {
                                Text(text = stringResource(R.string.detail_page_text_field_label))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            shape = RoundedCornerShape(10.dp),
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
                                        imageVector = Icons.Default.Send,
                                        contentDescription = "Send Comment",
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Add Comment",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                }
            }

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
                        LottieAnimationComposable(
                            animationResId = R.raw.lottie_eror_animation,
                        )
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
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = comment.content,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        fontWeight = FontWeight.Bold
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
