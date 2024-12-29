package com.cumaliguzel.apps.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IndicatorUI(
    //we define the parameters of the indicator :)
    pageSize : Int, //4
    currentPage : Int, //current index like flutter
    selectedColor : Color = MaterialTheme.colorScheme.primary,
    unselectedColor : Color = MaterialTheme.colorScheme.tertiary,
) {
    //we want to show the indicator in a row
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        //create a repeat works like a loop
        //after create box and update the state
        repeat(pageSize){
            //little spacer between the indicators
            Spacer(modifier = Modifier.size(2.5.dp))
            //create a box
            Box(modifier = Modifier
                .height(14.dp)
                .width(width = if (it == currentPage) 32.dp else 14.dp) //  if u r current page indicator get big
                .clip(RoundedCornerShape(10.dp))
                .background(color = if(it == currentPage) selectedColor else unselectedColor) // if u r current page indicator color change
            )
            Spacer(modifier = Modifier.size(2.5.dp))

        }
    }

}
//Preview part each pages u can see easily how it looks like :)
@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview1() {

    IndicatorUI(pageSize = 5, currentPage = 0)

}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview2() {

    IndicatorUI(pageSize = 5, currentPage = 1)

}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview3() {

    IndicatorUI(pageSize = 5, currentPage = 2)

}
@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview4() {

    IndicatorUI(pageSize = 5, currentPage = 3)
}
@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview5() {

    IndicatorUI(pageSize = 5, currentPage = 4)
}

