package ir.mrghost.gamebase.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.mrghost.gamebase.R
import ir.mrghost.gamebase.data.FakeData
import ir.mrghost.gamebase.data.Game
import ir.mrghost.gamebase.utils.HeaderIcon

@Composable
fun GameDetailScreen(modifier: Modifier, game: Game) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        HeaderIcon()
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(game.image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f),
                                Color.Black.copy(alpha = 0.8f)
                            )
                        )
                    ),
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
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
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
                            style = MaterialTheme.typography.bodyLarge,
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

        TextBox("Developer", game.developer)

        TextBox("Released Year", game.releaseYear.toString())

        TextBox("Genre", game.genre.toString())

        TextBox("Description", game.description)
    }

}

@Composable
private fun TextBox(label: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$label : ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

//@Preview(
//    showSystemUi = true
//)
//@Composable
//fun Test() {
//    GameDetailScreen(modifier = Modifier, game = FakeData.gameList[0])
//}