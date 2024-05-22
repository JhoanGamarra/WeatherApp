package com.jhoangamarra.wethearchallenge.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun ForecastScreen() {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(bottomBar = {

            NavigationBar(containerColor = Color(0xFF008D75)) {
                items.forEachIndexed { index, bottomNavigationItem ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            //navController.navigate(item.title)
                        },
                        label = { Text(text = bottomNavigationItem.title) },
                        icon = {
                            Box()
                            {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        bottomNavigationItem.selectedIcon
                                    } else {
                                        bottomNavigationItem.unselectedIcon
                                    }, contentDescription = bottomNavigationItem.title
                                )
                            }
                        })
                }
            }
        }) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center, text = "City Name"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Wind",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Temperature",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
                LazyColumn(
                    Modifier.padding(2.dp),
                    contentPadding = padding,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(duplicatedList) { item ->
                        Row(
                            modifier = Modifier
                                .background(Color(0xFF008D75))
                                .padding(7.dp)
                        ) {
                            Text(modifier = Modifier.weight(5f), text = item.hour)
                            Text(modifier = Modifier.weight(1f), text = item.temperature)
                            Icon(
                                modifier = Modifier.weight(1f),
                                imageVector = item.icon,
                                contentDescription = "content description"
                            )
                        }
                    }
                }
            }
        }
    }


}
