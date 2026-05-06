package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sun.rowset.internal.Row
import org.jetbrains.compose.resources.painterResource

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import org.example.project.ui.theme.getApplicationColorScheme


@Composable
fun ShoppingListElement(description: String) {
    Text(description)
}
data class ShoppingListItem(
    val description: String,
    val bought: Boolean = false
)
@Composable
fun ShoppingListElement(item: ShoppingListItem, onBoughtChange: (Boolean) -> Unit, onDelete: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = item.bought,
            onCheckedChange = onBoughtChange
        )
        Text(item.description, Modifier.weight(1f))
        IconButton(onClick = onDelete) {
            // contentDescription не отображается на экране, но читается средствами помощи слепым
            Icon(Icons.Default.Delete, contentDescription = "Удалить")
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val shoppingList = remember {
        mutableStateListOf(ShoppingListItem("Молоко"), ShoppingListItem("Мука"))
    }
    var newItemDesc by remember { mutableStateOf("") }
    LazyColumn {
        item {
            OutlinedTextField(
                value = newItemDesc, onValueChange = { newItemDesc = it },
                modifier = Modifier.padding(8.dp),
                label = {
                    Text("Название продукта")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (newItemDesc.isNotBlank()) {
                            shoppingList.add(ShoppingListItem(newItemDesc.trim()))
                            newItemDesc = ""
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить")
                    }
                })
        }

        itemsIndexed(shoppingList) { i, item ->
            ShoppingListElement(
                item,
                onBoughtChange = { shoppingList[i] = item.copy(bought = it) },
                onDelete = { shoppingList.removeAt(i) }
            )
        }
        }
    MaterialTheme (colorScheme = getApplicationColorScheme()){
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val lazyColumnState = rememberLazyListState()
        Scaffold (topBar = {
            TopAppBar(
                title = {
                    Text("Верхняя панель") },  actions = {
                    IconButton(onClick = {scope.launch {
                        snackbarHostState.showSnackbar("Снекбар", duration = SnackbarDuration.Long)
                    }
                        // Ничего не делает, просто пример
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить")
                    }
                    IconButton(onClick = {
                        // Ничего не делает, просто пример
                    }) {
                        Icon(Icons.Default.Remove, contentDescription = "Удалить")
                    }
                    Box {
                        var openMenu by remember { mutableStateOf(false) }
                        IconButton(onClick = {
                            openMenu = true
                        }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Остальное")
                        }
                        DropdownMenu(
                            expanded = openMenu,
                            onDismissRequest = { openMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("123") },
                                leadingIcon = { Icon(Icons.Default.Rocket, null) },
                                onClick = {
                                    openMenu = false // закрываем меню
                                    // ничего больше не делает, просто пример
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("123") },
                                leadingIcon = { Icon(Icons.Outlined.Settings, null) },
                                onClick = {
                                    openMenu = false
                                })
                        }
                    }
                    })

        }, snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = {
                        Text("Добавить")
                    },
                    icon = {
                        Icon(Icons.Default.Add, contentDescription = null)
                    },
                    onClick = {
                        // пример, ничего не делает
                    }
                )
        },
        ){ contentPadding ->
            var state by remember { mutableStateOf(false) }
            var showDialog by remember { mutableStateOf(false) }
            var counter by remember { mutableIntStateOf(0) }
            LazyColumn(
                contentPadding = contentPadding,
                state = lazyColumnState,
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Button(onClick = {
                        counter++
                    }) {
                        Text("Увеличить счётчик")
                    }
                    Button(onClick = {
                        showDialog = true
                    }) {
                        Text("Сбросить счётчик")
                    }
                    Text("Счётчик: $counter")

                    FilledTonalButton(onClick = {}) { Text("Заполненная тоновая") }
                    OutlinedButton(onClick = {}) { Text("Контурная") }
                    TextButton(onClick = {}) { Text("Текстовая") }

                    Spacer(Modifier.height(32.dp)) // пустое пространство

                    FilledTonalButton(onClick = {}) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Text("Заполненная тоновая с иконкой")
                    }
                    OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Text("Контурная с иконкой")
                    }
                }
            }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialog = false
                                counter = 0
                            }) {
                                Text("Подтвердить")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog = false
                            }) {
                                Text("Отменить")
                            }
                        },
                        text = {
                            Text("Прогресс увеличения будет потерян!")
                        },
                        title = {
                            Text("Сбросить счётчик?")
                        },
                        icon = {
                            Icon(Icons.Default.Warning, contentDescription = null)
                        })
                }
        }


                }


        }

//    val someList = remember { listOf() }
//
//    LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        items(someList,) {
//            ShoppingListElement(it, )





