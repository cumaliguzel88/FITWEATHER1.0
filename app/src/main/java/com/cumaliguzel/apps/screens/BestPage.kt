package com.cumaliguzel.apps.screens

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.cumaliguzel.apps.data.Clothes
import com.cumaliguzel.apps.viewModel.BestClothesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BestPage(
    viewModel: BestClothesViewModel,
    modifier: Modifier = Modifier
) {
    val bestClothesList by viewModel.bestClothesList.collectAsState()
    val selectedGender by viewModel.selectedGender.collectAsState()

    var selectedClothes by remember { mutableStateOf<Clothes?>(null) }
    val bottomSheetState = rememberModalBottomSheetState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        GenderSelectionDropdowns(
            selectedGender = selectedGender,
            onGenderSelected = { viewModel.setGender(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
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
                ClothesDetailsBottomSheets(clothes = selectedClothes!!)
            }
        }
    }
}

@Composable
fun GenderSelectionDropdowns(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { expanded = true }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (selectedGender == "male") Icons.Default.Man else Icons.Default.Woman,
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .padding(end = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Gender: ${if (selectedGender == "male") "Male" else "Female"}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Male") },
                onClick = {
                    onGenderSelected("male")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Female") },
                onClick = {
                    onGenderSelected("female")
                    expanded = false
                }
            )
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
                contentDescription = "com.cumaliguzel.apps.data.Clothes Image",
                modifier = Modifier.size(500.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}

@Composable
fun ClothesDetailsBottomSheets(clothes: Clothes) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "com.cumaliguzel.apps.data.Clothes Details",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        AsyncImage(
            model = clothes.img,
            contentDescription = "com.cumaliguzel.apps.data.Clothes Image",
            modifier = Modifier.fillMaxSize()
        )
    }
}
