package com.cumaliguzel.apps.onboarding

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonUI(
    text: String = "Next",
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.tertiary,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    fontSize: Float = 14f,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            style = textStyle,
            color = textColor
        )
    }
}


@Preview
@Composable
fun NextButtonPreview() {
    ButtonUI(text = "Next") {}
}

@Preview
@Composable
fun BackButtonPreview() {
    ButtonUI(
        text = "Back",
        backgroundColor = Color.Transparent,
        textColor = Color.Gray,
        textStyle = MaterialTheme.typography.bodySmall,
        fontSize = 13f
    ) {}
}
