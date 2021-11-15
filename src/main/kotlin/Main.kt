// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    var isActivated by remember { mutableStateOf(false) }

    var isHovered by remember { mutableStateOf(false) }

    val size by animateDpAsState(if (isActivated) 300.dp else 200.dp)

    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
            Box(
                Modifier
                    .size(size)
                    .background(color = if (isHovered) Color.Blue else Color.Red)
                    .pointerMoveFilter(onEnter = {
                        isHovered = true; false
                    }, onExit = {
                        isHovered = false; true
                    }).mouseClickable {
                        if (buttons.isPrimaryPressed) {
                            isActivated = true
                        } else if (buttons.isSecondaryPressed) {
                            isActivated = false
                        }
                    }
            )

        }
    }
}

fun main() = singleWindowApplication(title = "Scrollbars") {

    Box {

        val state = rememberLazyListState()

        LazyColumn(Modifier.fillMaxSize(), state) {

            items(1000000) { x ->

                Text("Item #$x")

            }

        }

        VerticalScrollbar(

            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),

            adapter = rememberScrollbarAdapter(state)

        )

    }

}