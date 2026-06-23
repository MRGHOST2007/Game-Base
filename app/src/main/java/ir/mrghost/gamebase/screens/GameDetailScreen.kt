package ir.mrghost.gamebase.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.mrghost.gamebase.R
import ir.mrghost.gamebase.data.local.Game
import ir.mrghost.gamebase.utils.HeaderIcon
import ir.mrghost.gamebase.utils.Utils
import ir.mrghost.gamebase.viewmodel.GameDetailViewModel

@Composable
fun GameDetailScreen(
    gameID: Long,
    viewModel: GameDetailViewModel = viewModel()
) {

    LaunchedEffect(gameID) {
        viewModel.loadGame(gameID)
    }

    val isFavorite by viewModel.isFavorite.collectAsState()
    val game by viewModel.game.collectAsState()

    if (game == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        GameDetailContent(
            game = game!!,
            isFavorite = isFavorite,
            onClick = {
                viewModel.toggleFavorite()
            }
        )
    }


}

@Composable
fun GameDetailContent(game: Game, isFavorite: Boolean, onClick: () -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AddFavoriteFAB(
                onClick,
                isFavorite
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {

            HeaderIcon()

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                val imageRes = if (game.image != 0) game.image else R.drawable.placeholder
                Image(
                    painter = painterResource(imageRes),
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
}

@Composable
fun AddFavoriteFAB(onClick: () -> Unit, isFavorite: Boolean) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth(.5f)
            .clip(CircleShape)
            .padding(vertical = 16.dp)
            .border(
                border = if (isFavorite) Utils.CustomBorder
                else BorderStroke(
                    width = 0.dp,
                    Color.Transparent,
                ),
                shape = CircleShape
            ),
        containerColor = if (isFavorite)
            MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        else
            Color.Yellow.copy(alpha = 0.7f)
    ) {
        Text(
            text = if (isFavorite) "Remove from favorites" else "Add to favorites",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )

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
//    showSystemUi = true,
//    uiMode = UI_MODE_NIGHT_YES
//)
//@Composable
//fun Test() {
//    GameDetailScreen(game = Game(
//        id = 1,
//        title = "Hollow Knight",
//        releaseYear = 2019,
//        developer = "Team Cherry",
//        genre = GameGenre.Platformer,
//        description = "Explore HollowNest as a nameless knight to discover its secrets.",
//        rating = 5.0f,
//        price = 14.99f,
//        image = R.drawable.hk
//    ))
//}