package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.UUID

data class TodoItem(val id: String, val value: String)

@Composable
fun App() {
    var todo by remember { mutableStateOf("") }

    var todoList by remember { mutableStateOf(listOf<TodoItem>()) }

    fun addTodoItem(id: String, value: String) {
        if (value.isEmpty()) return
        todoList = todoList + TodoItem(id, value)
        todo = ""
    }

    fun removeTodoItem(id: String) {
        todoList = todoList.filter { item -> item.id != id }
    }

    MaterialTheme {
        Scaffold(topBar = {
            Text(
                text = "Compose Todo App",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(16.dp)
            )
        }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    TextField(
                        value = todo,
                        onValueChange = { todo = it },
                        label = { Text("Enter Text") },
                        modifier = Modifier.width(200.dp),
                        singleLine = true
                    )
                    Button(onClick = {
                        addTodoItem(id = UUID.randomUUID().toString(), value = todo)
                    }) {
                        Text(
                            text = "Create task",
                        )
                    }
                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TodoListScreen(
                    todoList,
                    removeItem = { id -> removeTodoItem(id) })
            }
        }

    }

}

@Composable
fun TodoListScreen(todoList: List<TodoItem>, removeItem: (String) -> Unit = {}) {
    LazyColumn {
        items(todoList, key = { todo -> todo.id }) { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(MaterialTheme.colors.background),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.value,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { removeItem(task.id) },
                ) {
                    Text(
                        text = "Remove",
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}
