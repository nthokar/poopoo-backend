package ru.raperan.poopoo.screens

import FavoriteTracksScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ru.raperan.poopoo.viewmodels.FavoriteViewModel

@Composable
fun FavoriteScreen(
    navHostController: NavHostController,
    viewModel: FavoriteViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
//        Column(modifier = Modifier
//            .fillMaxSize()
//            .align(Alignment.Center),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally) {
//            Text("Favorite")
//        }
        FavoriteTracksScreen(navHostController, viewModel)

    }
}