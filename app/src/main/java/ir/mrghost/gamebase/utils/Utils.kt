package ir.mrghost.gamebase.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.mrghost.gamebase.R

class Utils {

    companion object {
        val CustomBorder = BorderStroke(
            2.dp, brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.25f),
                    Color.White.copy(alpha = 0.05f)
                )
            )
        )
    }

}

@Composable
fun HeaderIcon(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.gamebase),
            contentDescription = null
        )
    }
}

enum class GameGenre {
    Action,
    Adventure,
    Casual,
    Horror,
    Platformer,
    RPG,
    Simulation,
    Strategy
}