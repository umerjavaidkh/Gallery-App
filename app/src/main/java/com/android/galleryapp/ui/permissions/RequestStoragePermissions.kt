package com.android.galleryapp.ui.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestStoragePermissions(
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit
) {
    val context = LocalContext.current

    // Launchers for both READ_EXTERNAL_STORAGE
    val readStoragePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                onPermissionsGranted() // Notify permission granted
            } else {
                onPermissionsDenied() // Notify permission denied
            }
        }
    )
    LaunchedEffect(context) {
        // Check and request permissions
        val isReadGranted = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q ||
                PackageManager.PERMISSION_GRANTED == context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)

        if (!isReadGranted) {
            readStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }else{
            onPermissionsGranted() // Notify permission granted
        }
    }
}