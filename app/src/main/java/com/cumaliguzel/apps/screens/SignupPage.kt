package com.cumaliguzel.apps.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cumaliguzel.apps.R
import com.cumaliguzel.apps.data.WindowType
import com.cumaliguzel.apps.data.rememberWindowSize
import com.cumaliguzel.apps.viewModel.AuthState
import com.cumaliguzel.apps.viewModel.AuthViewModel

@Composable
fun SignupPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val windowSize = rememberWindowSize()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    val iconSize = when (windowSize.width) {
        WindowType.Compact -> 100.dp
        WindowType.Medium -> 120.dp
        WindowType.Expanded -> 140.dp
    }

    val textFontSize = when (windowSize.width) {
        WindowType.Compact -> 14.sp
        WindowType.Medium -> 16.sp
        WindowType.Expanded -> 18.sp
    }

    val buttonFontSize = when (windowSize.width) {
        WindowType.Compact -> 14.sp
        WindowType.Medium -> 16.sp
        WindowType.Expanded -> 18.sp
    }

    val verticalSpacing = when (windowSize.height) {
        WindowType.Compact -> 12.dp
        WindowType.Medium -> 16.dp
        WindowType.Expanded -> 20.dp
    }

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_LONG
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.LockOpen,
            contentDescription = "lock icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(iconSize)
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        Text(
            text = stringResource(R.string.signup_page_title),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = textFontSize
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(R.string.signup_page_email_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(17.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.signup_page_password_label)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(17.dp),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Key,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        Button(
            onClick = { authViewModel.signup(email, password) },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.signup_page_signup_button_label),
                fontWeight = FontWeight.Bold,
                fontSize = buttonFontSize,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}
