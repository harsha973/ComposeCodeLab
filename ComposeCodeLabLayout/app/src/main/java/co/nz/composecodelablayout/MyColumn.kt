package co.nz.composecodelablayout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nz.composecodelablayout.ui.theme.ComposeCodeLabLayoutTheme

@Composable
fun MyColumn(modifier: Modifier, content: @Composable () -> Unit) {
    Layout(
        content = content,
        modifier = modifier,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map {
                it.measure(constraints = constraints)
            }

            var y = 0
            layout(constraints.maxWidth, constraints.maxHeight) {
                placeables.forEach { placeable ->
                    placeable.placeRelative(0, y)
                    y += placeable.height
                }
            }
        })
}


@Preview
@Composable
fun MyCustomPreview() {
    ComposeCodeLabLayoutTheme {
        MyColumn(modifier = Modifier.padding(8.dp)) {
            Text(text = "Hello")
            Text(text = "demo of my")
            Text(text = "Custom column")
            Text(text = "places items")
            Text(text = "Vertically")
        }
    }
}