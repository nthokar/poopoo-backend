package ru.raperan.poopoo.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    selectedFilter: String, // Добавим параметр для текущего выбранного фильтра
    onFilterChange: (String) -> Unit, // Лямбда для изменения фильтра
    modifier: Modifier = Modifier,
    placeholder: String = "Введите запрос"
) {
    Column(modifier = modifier) {
        // Строка поиска
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = placeholder) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Кнопка поиска
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Поиск"
                )
            }
        }

        // Кнопки для фильтрации
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            // Кнопка "Треки"
            FilterButton(
                text = "Треки",
                isSelected = selectedFilter == "tracks",
                onClick = { onFilterChange("tracks") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Кнопка "Альбомы"
            FilterButton(
                text = "Альбомы",
                isSelected = selectedFilter == "albums",
                onClick = { onFilterChange("albums") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Кнопка "Исполнители"
            FilterButton(
                text = "Исполнители",
                isSelected = selectedFilter == "artists",
                onClick = { onFilterChange("artists") }
            )
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

