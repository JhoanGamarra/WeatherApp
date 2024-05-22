package com.jhoangamarra.wethearchallenge.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.jhoangamarra.wethearchallenge.LocationPermissionTextProvider
import com.jhoangamarra.wethearchallenge.MainViewModel
import com.jhoangamarra.wethearchallenge.PermissionDialog
import com.jhoangamarra.wethearchallenge.lib.navigation.composable
import com.jhoangamarra.wethearchallenge.weather.navigation.WelcomeRoute

private val permissionsToRequest = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
)
internal fun NavGraphBuilder.welcomeScreen(activity: Activity) = composable(route = WelcomeRoute) {
    val viewModel: MainViewModel = hiltViewModel(it)

    val state: MainViewModel.State by viewModel.state.collectAsState(MainViewModel.State())
    val events: MainViewModel.Event? by viewModel.events.collectAsState(initial = null)


    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            when {
                perms.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    //navController.navigate("LandingRoute")
                }
            }
            permissionsToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )


    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = LocationPermissionTextProvider(),
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(activity,
                    permission
                ),
                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(permissionsToRequest)
                },
                onGoToAppSettingsClick = {activity.openAppSettings()}
            )
        }

    WelcomeScreen{
        multiplePermissionResultLauncher.launch(permissionsToRequest)
    }
}


@Composable
fun WelcomeScreen(onOkClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "location icon"
        )
        Text(textAlign = TextAlign.Center,
            text = "Improve your app experience by enabling location permissions!",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "In this way, you can receive tasks and/or notify your position on the delivery route.\n\n" +
                    "1.\u2060 \u2060Tap on Enable location\n" +
                    "2.\u2060 \u2060Select the Permissions option\n" +
                    "3.\u2060 \u2060In the location permissions section, select allow all the time"
        )
        Button(onClick = {
            onOkClick()
        }) {
            Text(text = "Enable Location")
        }
    }

}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}