package ir.mrghost.gamebase.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.mrghost.gamebase.R
import ir.mrghost.gamebase.data.FakeData
import ir.mrghost.gamebase.data.Game
import ir.mrghost.gamebase.utils.GameGenre
import ir.mrghost.gamebase.utils.GameItem
import ir.mrghost.gamebase.utils.HeaderIcon
import ir.mrghost.gamebase.utils.TypeGameView
import ir.mrghost.gamebase.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesListScreen() {

    var query by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val data = FakeData.gameList
    var selectedGenres by remember { mutableStateOf(emptyList<GameGenre>()) }
    val searchedData: List<Game> = remember(query) {
        if (query.isBlank()) data
        else {
            data.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }
    val filteredData: List<Game> = remember(selectedGenres, searchedData) {
        if (selectedGenres.isEmpty()) {
            searchedData
        } else {
            searchedData.filter { game ->
                selectedGenres.contains(game.genre)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderIcon()

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.search_24), contentDescription = null
                    )
                },
                singleLine = true,
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = .75f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = .75f),
                    errorContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = .75f),
                    disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = .75f),
                ),
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        Utils.CustomBorder,
                        shape = CircleShape
                    )

            )

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = {
                    showBottomSheet = true
                }, modifier = Modifier
                    .size(56.dp)
                    .border(
                        Utils.CustomBorder,
                        shape = CircleShape
                    )
                    .clip(CircleShape), colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .75f),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ),
                shape = CircleShape,
                contentPadding = PaddingValues(12.dp)
            ) {
                Icon(
                    painterResource(R.drawable.filter_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Transparent)
                        .fillMaxSize(),
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(filteredData.size) {
                GameItem(filteredData[it], TypeGameView.Big)
            }

            item {
                HorizontalDivider(thickness = 200.dp, color = Color.Transparent)
            }

        }

    }

    if (showBottomSheet) {
        GenreDialog(
            sheetState,
            onDismiss = { showBottomSheet = false },
            onConfirm = {
                selectedGenres = it
                showBottomSheet = false
            },
            currentSelectedGenres = selectedGenres
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GenreDialog(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: (List<GameGenre>) -> Unit,
    currentSelectedGenres: List<GameGenre> = emptyList()
) {

    val selectedGenres = remember {
        mutableStateListOf<GameGenre>().apply {
            addAll(currentSelectedGenres)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        dragHandle = {
            BottomSheetDefaults.DragHandle()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Filter by Genre",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {

                items(GameGenre.entries.toList()) { genre ->
                    val selected = selectedGenres.contains(genre)
                    FilterChip(
                        selected = selected,
                        onClick = {
                            if (selectedGenres.contains(genre))
                                selectedGenres.remove(genre)
                            else
                                selectedGenres.add(genre)
                        },
                        label = {
                            Text(
                                text = genre.name, modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        shape = CircleShape,
                        border = if (selected) FilterChipDefaults.filterChipBorder(
                            selectedBorderColor = Color.Transparent,
                            enabled = true,
                            selected = selected
                        ) else Utils.CustomBorder,
                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    onClick = {
                        selectedGenres.clear()
                        onConfirm(emptyList())
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset")
                }
                Button(
                    onClick = {
                        onConfirm(selectedGenres.toList())
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Apply")
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