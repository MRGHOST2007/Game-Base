package ir.mrghost.gamebase.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ir.mrghost.gamebase.utils.GameItem
import ir.mrghost.gamebase.utils.HeaderIcon
import ir.mrghost.gamebase.utils.TypeGameView
import ir.mrghost.gamebase.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController) {

    val viewModel: FavoritesViewModel = viewModel()
    val favorites by viewModel.favorites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderIcon()

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favorites yet\nStart adding games you love!",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(
                    items = favorites,
                    key = { it.gameId }
                ) { favorite ->
                    val game = favorite.toGame()
                    GameItem(game, TypeGameView.Big, navController)
                }

                item {
                    HorizontalDivider(thickness = 200.dp, color = Color.Transparent)
                }
            }
        }

    }

}

//@Composable
//@Preview(
//    showBackground = true,
//    showSystemUi = true,
//)
//fun Test() {
//    GamesListScreen()
//}