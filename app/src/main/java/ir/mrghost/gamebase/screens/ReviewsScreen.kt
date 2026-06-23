package ir.mrghost.gamebase.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ir.mrghost.gamebase.utils.HeaderIcon
import ir.mrghost.gamebase.utils.ReviewCard
import ir.mrghost.gamebase.viewmodel.ReviewsViewModel
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(
    navController: NavController,
    viewModel: ReviewsViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded.value) {
            viewModel.loadReviews()
        }
    }
    val reviews by viewModel.reviews.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    val error by viewModel.error.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderIcon()

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = error!!,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = {
                                viewModel.loadReviews()
                            },
                            shape = CircleShape
                        ) {
                            Text(
                                "Retry",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            reviews.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No reviews available",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            else -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    items(
                        items = reviews,
                        key = { it.id }
                    ) { review ->
                        ReviewCard(
                            review = review,
                            onClick = {
                                val slug = review.slug
                                if (slug.isNotEmpty()) {
                                    val fullUrl = "https://gameblade.ir/$slug/"
                                    val encodedUrl = URLEncoder.encode(fullUrl, "UTF-8")
                                    navController.navigate("review/$encodedUrl")
                                } else {
                                    val encodedUrl = URLEncoder.encode(review.link, "UTF-8")
                                    navController.navigate("review/$encodedUrl")
                                }
                            })
                    }

                    item {
                        if (isLoadingMore) {
                            CircularProgressIndicator()
                        } else {
                            Button(
                                onClick = { viewModel.loadMore() },
                                modifier = Modifier.fillMaxWidth(0.7f)
                            ) {
                                Text("Load More")
                            }
                        }
                    }

                    item {
                        HorizontalDivider(thickness = 200.dp, color = Color.Transparent)
                    }
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