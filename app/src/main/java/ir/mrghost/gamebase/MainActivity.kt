package ir.mrghost.gamebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ir.mrghost.gamebase.screens.FavoritesScreen
import ir.mrghost.gamebase.screens.GameDetailScreen
import ir.mrghost.gamebase.screens.GamesListScreen
import ir.mrghost.gamebase.screens.HomeScreen
import ir.mrghost.gamebase.screens.ReviewDetailScreen
import ir.mrghost.gamebase.screens.ReviewsScreen
import ir.mrghost.gamebase.ui.theme.GameBaseTheme
import ir.mrghost.gamebase.utils.Utils
import kotlinx.coroutines.launch
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameBaseTheme(darkTheme = true, dynamicColor = false) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(navController)
                    }

                    composable(
                        "detail/{gameId}", arguments = listOf(
                            navArgument("gameId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val gameId = backStackEntry.arguments?.getLong("gameId") ?: 0L

                        GameDetailScreen(gameID = gameId)

                    }

                    composable(
                        "review/{reviewUrl}", arguments = (listOf(
                            navArgument("reviewUrl") {
                                type = NavType.StringType
                                defaultValue = ""
                            }))
                    ) { backStackEntry ->
                        val encodedUrl = backStackEntry.arguments?.getString("reviewUrl") ?: ""

                        if (encodedUrl.isNotBlank()){
                            val decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8")
                            ReviewDetailScreen(decodedUrl)
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun MainScreen(navController: NavController) {
        val pagerState = rememberPagerState(pageCount = { 4 }, initialPage = 0)

        Scaffold(
            modifier = Modifier.fillMaxSize(), floatingActionButton = {
                MainBottomAppBar(pagerState)
            }, floatingActionButtonPosition = FabPosition.Center
        ) { paddingValues ->
            HomeScreensPager(
                pagerState,
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

    @Composable
    fun HomeScreensPager(pagerState: PagerState, navController: NavController, modifier: Modifier) {
        HorizontalPager(state = pagerState, modifier = modifier) {
            when (it) {
                0 -> HomeScreen(navController)
                1 -> GamesListScreen(navController)
                2 -> FavoritesScreen(navController)
                3 -> ReviewsScreen(navController)
            }
        }
    }

    @Composable
    fun MainBottomAppBar(pagerState: PagerState) {

        val coroutine = rememberCoroutineScope()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                color = Color.Transparent,
                border = Utils.CustomBorder,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {

                BottomAppBar(
                    containerColor = BottomAppBarDefaults.containerColor.copy(alpha = .75f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NavigationBarItem(
                        selected = pagerState.currentPage == 0,
                        onClick = {
                            coroutine.launch {
                                pagerState.animateScrollToPage(
                                    0, animationSpec = spring(stiffness = Spring.StiffnessLow)
                                )
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.home_24),
                                contentDescription = null
                            )
                        },
                        label = { Text("Home", style = MaterialTheme.typography.bodyLarge) },
                        alwaysShowLabel = false
                    )
                    NavigationBarItem(
                        selected = pagerState.currentPage == 1,
                        onClick = {
                            coroutine.launch {
                                pagerState.animateScrollToPage(
                                    1, animationSpec = spring(stiffness = Spring.StiffnessLow)
                                )
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.controller_24),
                                contentDescription = null
                            )
                        },
                        label = { Text("Games", style = MaterialTheme.typography.bodyLarge) },
                        alwaysShowLabel = false
                    )
                    NavigationBarItem(
                        selected = pagerState.currentPage == 2,
                        onClick = {
                            coroutine.launch {
                                pagerState.animateScrollToPage(
                                    2, animationSpec = spring(stiffness = Spring.StiffnessLow)
                                )
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.star_24),
                                contentDescription = null
                            )
                        },
                        label = { Text("Favorites", style = MaterialTheme.typography.bodyLarge) },
                        alwaysShowLabel = false
                    )
                    NavigationBarItem(
                        selected = pagerState.currentPage == 3,
                        onClick = {
                            coroutine.launch {
                                pagerState.animateScrollToPage(
                                    3, animationSpec = spring(stiffness = Spring.StiffnessLow)
                                )
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.article_24),
                                contentDescription = null
                            )
                        },
                        label = { Text("Reviews", style = MaterialTheme.typography.bodyLarge) },
                        alwaysShowLabel = false
                    )
                }

            }
        }
    }
}