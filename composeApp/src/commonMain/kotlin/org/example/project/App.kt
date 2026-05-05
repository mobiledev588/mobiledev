package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform


@Composable
fun ShoppingListElement(description: String) {
    Text(description)
}

@Composable
fun App() {

    val someList = remember { listOf("Иванов А.С. — Вошёл", "Петрова Е.Н. — Вошла", "Сидоров К.В. — Вошёл",
        "Кузнецова М.А. — Вошла", "Смирнов Д.И. — Вошёл", "Васильев О.Л. — Вошёл","Петрова Е.Н. — Вышла",
        "Соколов И.Б. — Вошёл","Иванов А.С. — Вышел", "Михайлов П.Р. — Вошёл", "Смирнов Д.И. — Вышел",
        "Кузнецова М.А. — Вышла", "Новиков С.В. — Вошёл") }

    LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        items(someList,) {
            ShoppingListElement(it, )
        }
    }
}


