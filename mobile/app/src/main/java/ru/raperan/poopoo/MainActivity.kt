package ru.raperan.poopoo

import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.COMMAND_PLAY_PAUSE
import androidx.media3.common.Player.COMMAND_PREPARE
import androidx.media3.common.Player.COMMAND_SET_MEDIA_ITEM
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.raperan.poopoo.api.PlayService
import ru.raperan.poopoo.elements.PlayBar
import ru.raperan.poopoo.elements.PlayBarViewModel
import ru.raperan.poopoo.screens.FavoriteScreen
import ru.raperan.poopoo.screens.HomeScreen
import ru.raperan.poopoo.screens.RecomendScreen
import ru.raperan.poopoo.services.KeycloakAuthService
import ru.raperan.poopoo.ui.theme.PoopooTheme
import ru.raperan.poopoo.viewmodels.FavoriteViewModel
import java.util.UUID

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int?
)

class MainActivity(
    val apiUrl: String = "http://raperan.ru:1337"
) : ComponentActivity() {

    val items = listOf(
        BottomNavigationItem(
            "menu",
            Icons.Filled.Menu,
            Icons.Outlined.Menu,
            false,
            null),
        BottomNavigationItem(
            "home",
            Icons.Filled.Home,
            Icons.Outlined.Home,
            false,
            null),
        //
        BottomNavigationItem(
            "favorite",
            Icons.Filled.Favorite,
            Icons.Outlined.Favorite,
            false,
            null),
    )


    private lateinit var controller: MediaController
    val keycloakService = KeycloakAuthService()
    val playService = PlayService(keycloakAuthService = keycloakService)
    val playBarViewModel: PlayBarViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        val sessionToken =
            SessionToken(applicationContext, ComponentName(applicationContext, PlaybackService::class.java))
        val controllerFuture = MediaController.Builder(this, sessionToken)
            .buildAsync()
        controllerFuture.addListener(
            {
                controller = controllerFuture.get()
                initController()
                playBarViewModel.controller = controller
            },
            MoreExecutors.directExecutor()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PoopooTheme {
                val navigationController = rememberNavController()
                var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
                lateinit var favoriteViewModel: FavoriteViewModel

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar =  {
                        Column {
                            PlayBar(playBarViewModel)
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navigationController.navigate(item.title)
                                        },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (item.badgeCount != null) {
                                                        Badge {
                                                            Text(text = item.badgeCount.toString())
                                                        }
                                                    } else if (item.hasNews) {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (index == selectedItemIndex) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon,
                                                    contentDescription = item.title
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController = navigationController, startDestination = items[0].title, modifier = Modifier.padding(innerPadding)) {
                        //Список всех фильмов

                        composable(items[0].title ) { RecomendScreen() }

                        //Лента рекомендаций
                        composable(items[1].title ) { HomeScreen() }

                        //Отложенные
                        composable(items[2].title ) { FavoriteScreen(navigationController, favoriteViewModel) }

//                        composable(
//                            route = "movie_detail/{id}",
//                            arguments = listOf(navArgument("id") { type = NavType.IntType })
//                        ) { backStackEntry ->
//                            val movieId = backStackEntry.arguments?.getInt("id")
//                            if (movieId != null) {
//
//                                movieDetailsViewModel.movie = movieFinder.findMovie(movieId)
//
//                                MovieDetailScreen(movieDetailsViewModel, navigationController)
//                            } else {
//                                Text(text = "Ошибка загрузки фильма", style = MaterialTheme.typography.headlineLarge)
//                            }
//                        }
                    }
                }
            }
        }
    }

    fun pause() {
        if (controller.isPlaying) {
            controller.pause()
            log("Paused")
        } else{
            controller.play()
            log("Resumed")

        }
    }

    private fun play() {
        val collectionId: UUID = UUID.fromString("ca7b2634-6474-4c60-9912-e8938b1e12a1")
        GlobalScope.launch(Dispatchers.IO) {
            val music = playService.play(collectionId) // fetchData is a suspend function
            withContext(Dispatchers.Main) {
                if (music != null) {
                    log("before=${getStateName(controller.playbackState)}")
                    val mediaItem =
                        MediaItem.Builder()
                            .setMediaId("media-1")
                            .setUri(apiUrl + music.fileUrl)
                            .setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setArtist("David Bowie")
                                    .setTitle("Heroes")
                                    .build()
                            )
                            .build()

                    controller.clearMediaItems()
                    controller.setMediaItem(mediaItem)
                    controller.prepare()
                    controller.play()
                    log("after=${getStateName(controller.playbackState)}")
                }
            }
        }
    }

    private fun playNext() {
        val collectionId: UUID = UUID.fromString("ca7b2634-6474-4c60-9912-e8938b1e12a1")
        GlobalScope.launch(Dispatchers.IO) {
            val music = playService.playNext(collectionId) // fetchData is a suspend function
            withContext(Dispatchers.Main) {
                if (music != null) {
                    log("before=${getStateName(controller.playbackState)}")
                    val mediaItem =
                        MediaItem.Builder()
                            .setMediaId("media-1")
                            .setUri(apiUrl + music.fileUrl)
                            .setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setArtist("David Bowie")
                                    .setTitle("Heroes")
                                    .build()
                            )
                            .build()

                    controller.setMediaItem(mediaItem)
                    controller.prepare()
                    controller.play()
                    log("after=${getStateName(controller.playbackState)}")
                }
            }
        }
    }

    private fun initController() {
        //controller.playWhenReady = true
        controller.addListener(object : Player.Listener {

            override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                super.onMediaMetadataChanged(mediaMetadata)
                log("onMediaMetadataChanged=$mediaMetadata")
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                log("onIsPlayingChanged=$isPlaying")
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == 4) {
                    //stateEnded
                    playNext()

                }
                log("onPlaybackStateChanged=${getStateName(playbackState)}")
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                log("onPlayerError=${error.stackTraceToString()}")
            }

            override fun onPlayerErrorChanged(error: PlaybackException?) {
                super.onPlayerErrorChanged(error)
                log("onPlayerErrorChanged=${error?.stackTraceToString()}")
            }
        })
        log("COMMAND_PREPARE=${controller.isCommandAvailable(COMMAND_PREPARE)}")
        log("COMMAND_SET_MEDIA_ITEM=${controller.isCommandAvailable(COMMAND_SET_MEDIA_ITEM)}")
        log("COMMAND_PLAY_PAUSE=${controller.isCommandAvailable(COMMAND_PLAY_PAUSE)}")
    }

    private fun getStateName(i: Int): String? {
        return when (i) {
            1 -> "STATE_IDLE"
            2 -> "STATE_BUFFERING"
            3 -> "STATE_READY"
            4 -> "STATE_ENDED"
            else -> null
        }
    }

    private fun log(message: String) {
        Log.e("=====[TestMedia]=====", message)
    }

}