package ir.mrghost.gamebase.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ir.mrghost.gamebase.R
import ir.mrghost.gamebase.data.local.Game

@Composable
fun GameItem(game: Game, typeGameView: TypeGameView, navController: NavController) {
    when (typeGameView) {
        TypeGameView.Compact -> {
            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(135.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.BottomCenter
            ) {
                ContentOfItem(game, navController)
            }
        }

        TypeGameView.Big -> {
            Box(
                modifier = Modifier
                    .width(320.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.BottomCenter
            ) {
                ContentOfItem(game, navController)
            }
        }
    }

}

@Composable
private fun ContentOfItem(game: Game, navController: NavController) {
    Image(
        painter = painterResource(game.image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxSize()
            .clickable(onClick = {
                navController.navigate("detail/${game.id}")
            })
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.5f),
                        Color.Black.copy(alpha = 0.8f)
                    )
                )
            )
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = game.title,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(.7f),
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = game.rating.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                )
                val starIcon =
                    if (game.rating == 5f) R.drawable.star_24 else R.drawable.star_half_24
                Icon(
                    painter = painterResource(starIcon),
                    contentDescription = null,
                    tint = Color.Yellow
                )
            }
        }
    }
}

enum class TypeGameView {
    Compact,
    Big
}

//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//@Composable
//fun Test(){
//    GameItem(FakeData.gameList[3], TypeGameView.Big)
//}