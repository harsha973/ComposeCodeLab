package co.nz.composecodelablayout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import co.nz.composecodelablayout.ui.theme.ComposeCodeLabLayoutTheme


@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button, text, button2) = createRefs()

        Button(onClick = { },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top)
            }) {
            Text(text = "Button")
        }

        Text(text = "Text",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(button.bottom)
                centerAround(button.end)
            })

        val barrier = createEndBarrier(button, text)

        Button(onClick = { },
            modifier = Modifier.constrainAs(button2) {
                start.linkTo(barrier)
            }) {
            Text(text = "Button2")
        }
    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    ComposeCodeLabLayoutTheme {
        ConstraintLayoutContent()
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(0.5F)
        Text(
            modifier = Modifier.constrainAs(text) {
                start.linkTo(guideline)
            },
            text = "This very very very very very very very very very very very very very large text"
        )
    }
}

@Preview
@Composable
fun LargeConstraintLayoutPreview() {
    ComposeCodeLabLayoutTheme {
        LargeConstraintLayout()
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(16.dp)
        } else {
            decoupledConstraints(32.dp)
        }

        ConstraintLayout(constraintSet = constraints) {
            Button(
                onClick = { },
                modifier = Modifier.layoutId("Button")
            ) {
                Text(text = "Button")
            }

            Text(
                text = "Text",
                modifier = Modifier.layoutId("Text")
            )
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("Button")
        val text = createRefFor("Text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }

        constrain(text) {
            top.linkTo(button.bottom, margin = margin)
            centerHorizontallyTo(parent)
        }
    }
}

@Preview
@Composable
fun DecoupledConstraintLayoutPreview() {
    ComposeCodeLabLayoutTheme {
        DecoupledConstraintLayout()
    }
}