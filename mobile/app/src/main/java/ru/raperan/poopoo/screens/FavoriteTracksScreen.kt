import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.raperan.poopoo.elements.TrackItem
import ru.raperan.poopoo.viewmodels.FavoriteViewModel

@Composable
fun FavoriteTracksScreen(
    navigator: NavHostController,
    favoriteViewModel: FavoriteViewModel
) {
    val scrollPosition by rememberSaveable { mutableIntStateOf(favoriteViewModel.scrollPosition) }

    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition
    )


    Column(modifier = Modifier.fillMaxSize()) {

        val loadMore = remember {
            derivedStateOf {
                val layoutInfo = scrollState.layoutInfo
                val totalItemsNumber = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

                lastVisibleItemIndex > (totalItemsNumber - 2)
            }
        }

        Text(
            text = "Ваше любимое",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )


        LaunchedEffect(scrollState) {
            snapshotFlow {
                scrollState.firstVisibleItemIndex
            }
                .collect{ index ->
                    favoriteViewModel.scrollPosition = index
                }
        }

        LazyColumn(state = scrollState, modifier = Modifier.weight(1f)) {


            items(favoriteViewModel.tracks) { track ->
                TrackItem(
                    track = track,
                    onClick = {
                    favoriteViewModel.playFavoriteFromTrack(track)
                    },
                    onFavoriteClick = {
                        track.isFavorite = !track.isFavorite
                        favoriteViewModel.setIsFavorite(track, track.isFavorite)
                    }
                )
            }

            // Когда пользователь достигает конца списка, добавляются новые фильмы
            item {
                if (loadMore.value) {
                    LaunchedEffect(Unit) {
//                        delay(1000L) // Имитация задержки загрузки данных
//                        for (i in viewModel.tracks.size until viewModel.tracks.size + 10) {
//                            viewModel.tracks.add(Track(poster = "https://avatars.mds.yandex.net/get-kinopoisk-image/9784475/0c67265b-6631-4e25-b89c-3ddf4e5a1ee7/1920x",
//                                trackName = "ride the lighting",
//                                authorName = "Metallica"
//                                ))
//                        }
                    }
                }

                Box(Modifier.height(56.dp))
            }
        }
    }


}

