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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.jhoangamarra.wethearchallenge.MainViewModel
import com.jhoangamarra.wethearchallenge.lib.navigation.composable
import com.jhoangamarra.wethearchallenge.weather.navigation.LandingRoute


internal fun NavGraphBuilder.landingScreen() = composable(route = LandingRoute) {
    /*val viewModel: MainViewModel = hiltViewModel(it)

    val state: MainViewModel.State by viewModel.state.collectAsState(MainViewModel.State())
    val events: MainViewModel.Event? by viewModel.events.collectAsState(initial = null)
*/
    LandingScreen()
}

@Composable
fun LandingScreen() {

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


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean
)

data class HourWeatherItem(val hour: String, val temperature: String, val icon: ImageVector)


val itemList = listOf(
    HourWeatherItem("6:00 AM", "60° F", Icons.Filled.Info),
    HourWeatherItem("7:00 AM", "66° F", Icons.Filled.Info),
    HourWeatherItem("8:00 AM", "64° F", Icons.Filled.Info),
    HourWeatherItem("9:00 AM", "62° F", Icons.Filled.Info),
    HourWeatherItem("10:00 AM", "61° F", Icons.Filled.Info)
)

val duplicatedList = List(5) { itemList }.flatten()

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false
    ),
    BottomNavigationItem(
        title = "Forecast",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange,
        hasNews = false
    )
)
