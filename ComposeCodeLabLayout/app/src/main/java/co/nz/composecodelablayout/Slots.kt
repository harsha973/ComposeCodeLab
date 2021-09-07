package co.nz.composecodelablayout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.nz.composecodelablayout.ui.theme.ComposeCodeLabLayoutTheme

@Composable
fun Slots(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab", style = MaterialTheme.typography.h6)
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    label = {
                        Text("Account")
                    },
                    icon = {
                        Icons.Filled.AccountBox
                    },
                    selected = true,
                    onClick = { /*TODO*/ },
                    selectedContentColor = MaterialTheme.colors.onPrimary,
                    unselectedContentColor = MaterialTheme.colors.onPrimary,
                )
                BottomNavigationItem(
                    icon = {
                        Icons.Filled.Call
                    },
                    label = {
                        Text("Call")
                    },
                    selected = false, onClick = { /*TODO*/ }
                )
                BottomNavigationItem(
                    icon = {
                        Icons.Filled.Star
                    },
                    label = {
                        Text("Start")
                    },
                    selected = false, onClick = { /*TODO*/ }
                )
            }
        }
    ) { padding ->
        BodyContent(Modifier.padding(padding))
    }
}

@Composable
private fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hi there")
        Text(text = "This is second text")
    }
}

@Preview
@Composable
fun LayoutCodeLabPreview() {
    ComposeCodeLabLayoutTheme {
        Slots()
    }
}