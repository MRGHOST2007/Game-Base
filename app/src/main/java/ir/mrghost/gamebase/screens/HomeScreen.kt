package ir.mrghost.gamebase.screens

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.mrghost.gamebase.R
import ir.mrghost.gamebase.data.FakeData
import ir.mrghost.gamebase.utils.GameGenre
import ir.mrghost.gamebase.utils.GameItem
import ir.mrghost.gamebase.utils.HeaderIcon
import ir.mrghost.gamebase.utils.TypeGameView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {

    val scrollState = rememberScrollState()

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
                TopPager()
            }

            ListRows()

        }
    }
}

@Composable
private fun TopPager() {
    val pager = rememberPagerState() { FakeData.topPager.size }
    val coroutine = rememberCoroutineScope()

    Box(
        Modifier
            .width(320.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(pager, modifier = Modifier.fillMaxSize()) {
            GameItem(FakeData.topPager[it], TypeGameView.Big)
        }
    }

    LaunchedEffect(Unit) {
        coroutine.launch {
            while (true) {
                delay(3000)
                if (pager.currentPage == 2) {
                    pager.animateScrollToPage(
                        0,
                        animationSpec = spring(stiffness = Spring.StiffnessMedium)
                    )
                    delay(3000)
                }
                pager.animateScrollToPage(
                    pager.currentPage + 1,
                    animationSpec = spring(stiffness = Spring.StiffnessLow)
                )
            }
        }
    }

}

@Composable
private fun ListRows() {

    val dataRow1 = FakeData.gameList.filter { it.genre == GameGenre.Action }
    val dataRow2 = FakeData.gameList.filter { it.genre == GameGenre.Platformer }
    val dataRow3 = FakeData.gameList.filter { it.genre == GameGenre.RPG }

    Text(text = "Game Awards 2026")
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(FakeData.gameAwards.size) {
            GameItem(FakeData.gameAwards[it], TypeGameView.Compact)
        }
    }

    Text(text = "Best Actions")
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dataRow1.size) {
            GameItem(dataRow1[it], TypeGameView.Compact)
        }
    }

    Text(text = "Best Platformers")
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dataRow2.size) {
            GameItem(dataRow2[it], TypeGameView.Compact)
        }
    }

    Text(text = "Best Role-Plays")
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dataRow3.size) {
            GameItem(dataRow3[it], TypeGameView.Compact)
        }
    }
}