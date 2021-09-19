/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.state.util.generateRandomTodoItem
import kotlin.random.Random

/**
 * Stateless component that is responsible for the entire todo screen.
 *
 * @param items (state) list of [TodoItem] to display
 * @param onAddItem (event) request an item be added
 * @param onRemoveItem (event) request an item be removed
 */
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    currentEditingItem: TodoItem?,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
    onEditItemStarted: (TodoItem) -> Unit,
    onItemChanged: (TodoItem) -> Unit,
    onEditItemCompleted: () -> Unit,
) {
    Column {
        if (currentEditingItem == null) {
            TodoItemInputBackground(elevate = true) {
                TodoItemEntryInput(onAddItem)
            }
        } else {
            Text(
                text = "Editing",
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = items) { todoItem ->
                if (currentEditingItem?.id == todoItem.id) {
                    TodoItemInlineEditor(
                        item = todoItem,
                        onItemChanged = onItemChanged,
                        onEntryComplete = onEditItemCompleted,
                        onRemoveItem = onRemoveItem
                    )
                } else {
                    TodoRow(
                        todo = todoItem,
                        onItemClicked = { onEditItemStarted(it) },
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }

        // For quick testing, a random item generator button
        Button(
            onClick = { onAddItem(generateRandomTodoItem()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text("Add random item")
        }
    }
}

@Composable
fun TodoItemInlineEditor(
    item: TodoItem,
    onItemChanged: (TodoItem) -> Unit,
    onEntryComplete: () -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TodoItemInput(
            modifier = Modifier.weight(1f),
            text = item.task,
            onTextChange = { onItemChanged(item.copy(task = it)) },
            onEntryCompleted = onEntryComplete,
            isIconVisible = true,
            icon = item.icon,
            onIconChange = { onItemChanged(item.copy(icon = it)) },
        )
        TextButton(
            onClick = { onRemoveItem(item) },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "❌",
                textAlign = TextAlign.End,
                modifier = Modifier.width(30.dp)
            )
        }
    }

}

@Composable
fun TodoItemEntryInput(
    onItemComplete: (TodoItem) -> Unit,
) {
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    val isIconVisible = text.isNotBlank()
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }

    TodoItemInput(
        text = text,
        onTextChange = setText,
        onEntryCompleted = submit,
        isIconVisible = isIconVisible,
        icon = icon,
        onIconChange = setIcon
    )
}

@Composable
private fun TodoItemInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onEntryCompleted: () -> Unit,
    isIconVisible: Boolean,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(16.dp)) {
            TodoInputText(
                text = text,
                onTextChange = onTextChange,
                onImeAction = onEntryCompleted,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            TodoEditButton(
                onClick = onEntryCompleted,
                text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = text.isNotBlank()
            )
        }

        if (isIconVisible) {
            AnimatedIconRow(icon = icon, onIconChange = onIconChange)
        } else {
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

/**
 * Stateless composable that displays a full-width [TodoItem].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha: Float = remember(todo.id) { randomTint() }
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(todo.task)
        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(iconAlpha),
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, null, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    Surface {
        TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
fun PreviewTodoInputItem() {
    Surface {
        TodoItemInputBackground(elevate = true) {
            TodoItemEntryInput(onItemComplete = {})
        }
    }
}

@Preview
@Composable
fun TodoInputFieldTextPreview() {
    val (text, setText) = remember { mutableStateOf("") }
    Surface {
        TodoItemInputBackground(elevate = true) {
            TodoInputText(text = text, onTextChange = setText)
        }
    }
}