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

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

//    private var _todoItems = MutableLiveData(listOf<TodoItem>())
//    val todoItems: LiveData<List<TodoItem>> = _todoItems

    private val currentEditingIndex = mutableStateOf(-1)

    var todoItems = mutableStateListOf<TodoItem>()
        private set

    val currentEditingItem: TodoItem?
        get() = if (currentEditingIndex.value == -1) null else todoItems[currentEditingIndex.value]

    fun addItem(item: TodoItem) {
        todoItems.add(item)
    }

    fun removeItem(item: TodoItem) {
        todoItems.remove(item)
    }

    fun onEditItemStarted(item: TodoItem) {
        currentEditingIndex.value = todoItems.indexOf(item)
    }

    fun onItemChanged(item: TodoItem) {
        val currentItem = requireNotNull(currentEditingItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }
        todoItems[currentEditingIndex.value] = item
    }

    fun onEditItemCompleted() {
        currentEditingIndex.value = -1
    }
}
