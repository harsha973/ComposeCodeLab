package co.nz.composecodelablayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nz.composecodelablayout.ui.theme.ComposeCodeLabLayoutTheme
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

@Composable
fun ImageList(modifier: Modifier = Modifier) {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Scroll to start")
            }
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text(text = "Scroll to end")
            }
        }
        LazyColumn(modifier, state = scrollState) {
            items(100) {
                ImageListItem(item = it.toString())
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageListItem(modifier: Modifier = Modifier, item: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Image(
            painter = rememberImagePainter(
                "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android icon",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Item #$item", modifier = Modifier.padding(8.dp))
    }
}


@Preview
@Composable
fun SimpleListPreview() {
    ComposeCodeLabLayoutTheme {
        ImageList()
    }
}