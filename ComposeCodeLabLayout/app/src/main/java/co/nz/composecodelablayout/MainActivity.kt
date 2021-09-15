package co.nz.composecodelablayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nz.composecodelablayout.ui.theme.ComposeCodeLabLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodeLabLayoutTheme {
                IntrinsicHeight()
            }
        }
    }
}

@Composable
fun IntrinsicHeight() {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Text(
            text = "Hi",
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            textAlign = TextAlign.Start
        )
        Divider(
            color = Color.Black, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            text = "There",
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(4.dp)
                .weight(1f)
        )
    }
}

@Preview
@Composable
fun IntrinsicHeightPreview() {
    ComposeCodeLabLayoutTheme {
        IntrinsicHeight()
    }
}