package com.octaneocatane.weather.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.octaneocatane.weather.presentation.DialogManager
import com.octaneocatane.weather.presentation.fragments.MainFragment

fun Fragment.isPermissionGranted(permissionName: String): Boolean {
    return ContextCompat.checkSelfPermission(
        activity as AppCompatActivity, permissionName) == PackageManager.PERMISSION_GRANTED
}

fun showSnackbar(view: View, message: String) {
    return Snackbar.make(view.rootView, message, Snackbar.LENGTH_SHORT).show()
}

fun showToast(context: Activity, status: Boolean) {
    return Toast.makeText(context, "${MainFragment.PERMISSION_STATUS_TEXT} is $status", Toast.LENGTH_LONG).show()
}

fun Fragment.showLocationSettingsDialog() {
    return DialogManager.locationSettingsDialog(requireContext(), object : DialogManager.Listener {
        override fun onClick(name: String?) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    })
}

fun Fragment.showNetworkSettingsDialog() {
    return DialogManager.networkSettingsDialog(requireContext(), object : DialogManager.Listener {
        override fun onClick(name: String?) {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
    })
}





