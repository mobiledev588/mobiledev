package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
        Scaffold { contentPadding ->
            var state by remember { mutableStateOf(false) }
            Column(Modifier.padding(contentPadding)) {
                Button(onClick = { state = !state }) {
                    Text("Переключить")
                }
                Text("Состояние: $state")
            }
        }
    }


//    val someList = remember { listOf() }
//
//    LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        items(someList,) {
//            ShoppingListElement(it, )
        }




