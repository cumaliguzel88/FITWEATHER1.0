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

// we crate two buttons back and next -> start
@Composable
fun ButtonUI(
    text: String = "Next",
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.tertiary,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    fontSize: Int = 14,
    onClick: () -> Unit,
)
{
    //button
    Button(
        onClick = onClick, colors = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primary
        ), shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text, fontSize = fontSize.sp, style = textStyle, color = MaterialTheme.colorScheme.tertiary
        )


    }

}

//Preview part each buttons u can see easily how it looks like :)
@Preview
@Composable
fun NextButton() {
    ButtonUI (text = "Next") {
    }

}

@Preview
@Composable
fun BackButton() {
    ButtonUI(text = "Back",
        backgroundColor = Color.Transparent,
        textColor = Color.Gray,
        textStyle = MaterialTheme.typography.bodySmall,
        fontSize = 13) {
    }
}