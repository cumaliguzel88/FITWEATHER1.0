package com.cumaliguzel.apps.screens

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.cumaliguzel.apps.components.ClothesDetailsBottomSheet
import com.cumaliguzel.apps.components.GenderSelectionDropdown
import com.cumaliguzel.apps.data.Clothes
import com.cumaliguzel.apps.data.WindowType
import com.cumaliguzel.apps.data.rememberWindowSize
import com.cumaliguzel.apps.viewModel.BestClothesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BestPage(
    viewModel: BestClothesViewModel,
    modifier: Modifier = Modifier
) {
    val bestClothesList by viewModel.bestClothesList.collectAsState()
    val selectedGender by viewModel.selectedGender.collectAsState()
    val windowSize = rememberWindowSize()

    var selectedClothes by remember { mutableStateOf<Clothes?>(null) }
    val bottomSheetState = rememberModalBottomSheetState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        GenderSelectionDropdown(
            selectedGender = selectedGender,
            onGenderSelected = { viewModel.setGender(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val columnCount = when (windowSize.width) {
            WindowType.Compact -> 2
            WindowType.Medium -> 3
            WindowType.Expanded -> 4
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columnCount),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(bestClothesList) { clothes ->
                ClothesCards(clothes = clothes) {
                    selectedClothes = clothes
                }
            }
        }

        if (selectedClothes != null) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = { selectedClothes = null }
            ) {
                ClothesDetailsBottomSheet(clothes = selectedClothes!!)
            }
        }
    }
}

@Composable
fun ClothesCards(clothes: Clothes, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = clothes.img,
                contentDescription = "",
                modifier = Modifier.size(400.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
