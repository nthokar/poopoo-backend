package ru.raperan.poopoo.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.launch

@Composable
fun PlayBar(
    model: PlayBarViewModel,
) {
    var currentProgress by remember { mutableStateOf(model.currentProgress) }
    model.start { currentProgress = it }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    ConstraintLayout {

        val (screen, box, bar) = createRefs()

        Box(modifier = Modifier
            .fillMaxSize()
//            .fillMaxWidth()
//            .height(120.dp)
            .constrainAs(screen)
        {
            bottom.linkTo(parent.bottom)
        }) {
        }

        Box(modifier = Modifier
//            .fillMaxSize()
            .background(Color.Gray)
            .fillMaxWidth()
            .height(120.dp)
            .constrainAs(box)
            {
                bottom.linkTo(box.bottom)
            }) {
        }

        LinearProgressIndicator(
            progress = { currentProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .constrainAs(bar) {
                    top.linkTo(box.top)
                }
            ,
            color = Color(0xFF03DAC5)
        )

//            Button(onClick = {
//                loading = true
//                scope.launch {
////                loadProgress({p -> currentProgress = p}, { (model.controller?.currentPosition / model.controller.duration).toFloat() })
////                loadProgress({p -> currentProgress = p}, progressFetcher, controller)
//                    loadProgress({p -> currentProgress = p}, model)
//                    loading = false // Reset loading when the coroutine finishes
//                }
//            }, enabled = !loading) {
//                Text("Start loading")
//            }
//
//            LinearProgressIndicator(
//                progress = { currentProgress },
//                modifier = Modifier.fillMaxWidth(),
//            )
//
//            if (model.controller != null) {
//                Text(model.controller!!.currentMediaItem!!.mediaMetadata.title.toString())

    }
}
