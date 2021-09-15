package co.nz.composecodelablayout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.nz.composecodelablayout.ui.theme.ComposeCodeLabLayoutTheme

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
private fun GridChip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier.padding(8.dp),
        border = BorderStroke(width = Dp.Hairline, Color.Black),
        shape = RoundedCornerShape(4.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .width(16.dp)
                    .height(16.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(modifier = modifier, text = text)
        }

    }
}

@Composable
private fun Body(modifier: Modifier = Modifier) {
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        StaggeredGrid(rows = 5) {
            topics.forEach { GridChip(text = it) }
        }
    }
}

@Composable
private fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    // Holds width of each row
    val rowWidths = IntArray(rows) { 0 }
    // Holds height of each row
    val rowHeights = IntArray(rows) { 0 }

    // Holds X position of each row
    val rowX = IntArray(rows) { 0 }
    // Holds Y position of each row
    val rowY = IntArray(rows) { 0 }

    Layout(content = content,
        modifier = modifier,
        measurePolicy = { measurables, constraints ->

            val placeables = measurables.mapIndexed { index, measurable ->
                val placeable = measurable.measure(constraints = constraints)
                val row = index % rows // vertical staggering

                // add placeable width to row width
                rowWidths[row] += placeable.width
                rowHeights[row] = maxOf(rowHeights[row], placeable.height)
                placeable
            }

            // total layout width - max of all row widths and should fall in constraints range
            val width =
                rowWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                    ?: constraints.minWidth
            // total layout height - sum of all row heights and should fall in constraints range
            val height =
                rowHeights.sumOf { it }.coerceIn(constraints.minHeight, constraints.maxHeight)

            for (index in 1 until rows) {
                // Row y is placed at bottom of previous row
                rowY[index] = rowY[index - 1] + rowHeights[index - 1]
            }

            layout(width, height) {
                placeables.forEachIndexed { index, placeable ->
                    val row = index % rows

                    placeable.placeRelative(
                        rowX[row],
                        rowY[row]
                    )

                    // increment rowX to place next item
                    rowX[row] += placeable.width
                }
            }
        })
}

@Preview
@Composable
fun StaggeredGridPreview() {
    ComposeCodeLabLayoutTheme {
        Body()
    }
}