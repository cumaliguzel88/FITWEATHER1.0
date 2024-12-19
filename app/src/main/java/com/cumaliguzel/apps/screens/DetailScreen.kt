package com.cumaliguzel.apps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil3.compose.AsyncImage
import com.cumaliguzel.apps.data.Clothes
import com.cumaliguzel.apps.viewModel.ClothesViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    clothesViewModel: ClothesViewModel,
    clothes: Clothes,
    navController: NavController
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Clothes Details",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Kıyafet görseli
            item {
                AsyncImage(
                    model = clothes.img,
                    contentDescription = "Clothes Image",
                    modifier =Modifier.size(450.dp)

                )
            }
            // Butonlar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { clothesViewModel.openTopLink(clothes.topLink) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onTertiary)
                    ) {
                        Text(text = "👕 Top Link")
                    }
                    Button(
                        onClick = { clothesViewModel.openBottomLink(clothes.bottomLink) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onTertiary)
                    ) {
                        Text(text = "👖 Bottom Link")
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTopAppBar(
    title: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Daha kompakt yükseklik
            .background(MaterialTheme.colorScheme.onTertiary), // Arka plan rengi
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onPrimary // İkon rengi
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium, // Orta boy başlık
            color = MaterialTheme.colorScheme.onPrimary, // Metin rengi
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


