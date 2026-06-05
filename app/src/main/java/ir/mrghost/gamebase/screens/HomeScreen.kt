package ir.mrghost.gamebase.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ir.mrghost.gamebase.utils.GameItem
import ir.mrghost.gamebase.utils.HeaderIcon
import ir.mrghost.gamebase.utils.TypeGameView
import ir.mrghost.gamebase.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController) {

    val scrollState = rememberScrollState()
    val viewModel: HomeViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderIcon()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, bottom = 128.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            Box(
                Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                TopPager(viewModel = viewModel, navController)
            }

            ListRows(viewModel, navController)

        }
    }
}

@Composable
private fun TopPager(viewModel: HomeViewModel, navController: NavController) {
    val topPagerGames by viewModel.topPagerGames.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { topPagerGames.size }
    )

    Box(
        Modifier
            .width(320.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(pagerState, modifier = Modifier.fillMaxSize()) {
            GameItem(topPagerGames[it], TypeGameView.Big, navController = navController)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            // Check if we're still active before scrolling
            if (pagerState.pageCount > 0) {
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(
                    nextPage,
                    animationSpec = spring(stiffness = Spring.StiffnessLow)
                )
            }
        }
    }

}

@Composable
private fun ListRows(viewModel: HomeViewModel, navController: NavController) {

    val gameAwards by viewModel.gameAwards.collectAsState()
    val actionGames by viewModel.actionGames.collectAsState()
    val platformerGames by viewModel.platformerGames.collectAsState()
    val rpgGames by viewModel.rpgGames.collectAsState()


    Text(text = "Game Awards 2026")
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = gameAwards,
            key = { it.id }
        ) {
            GameItem(it, TypeGameView.Compact, navController)
        }
    }

    Text(text = "Best Actions")
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = actionGames,
            key = { it.id }
        ) {
            GameItem(it, TypeGameView.Compact, navController)
        }
    }

    Text(text = "Best Platformers")
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = platformerGames,
            key = { it.id }
        ) {
            GameItem(it, TypeGameView.Compact, navController)
        }
    }

    Text(text = "Best Role-Plays")
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = rpgGames,
            key = { it.id }
        ) {
            GameItem(it, TypeGameView.Compact, navController)
        }
    }
}