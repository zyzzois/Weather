package com.octaneocatane.weather.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.octaneocatane.weather.R
import com.octaneocatane.weather.presentation.DialogManager
import com.octaneocatane.weather.presentation.fragments.MainFragment
import com.octaneocatane.weather.utils.Constants.PERMISSION_STATUS_TEXT

fun Fragment.isPermissionGranted(permissionName: String): Boolean {
    return ContextCompat.checkSelfPermission(
        activity as AppCompatActivity, permissionName) == PackageManager.PERMISSION_GRANTED
}

fun showSnackbar(view: View, message: String, resources: Resources) {

    return Snackbar.make(view.rootView, message, Snackbar.LENGTH_SHORT)
        .setAnimationMode(ANIMATION_MODE_SLIDE)
        .setTextColor(resources.getColor(R.color.default_android_text_color))
        .setBackgroundTint(resources.getColor(R.color.light_blue)).show()
}

fun showToast(context: Activity, status: Boolean) {
    return Toast.makeText(context, "$PERMISSION_STATUS_TEXT is $status", Toast.LENGTH_LONG).show()
}

fun Fragment.showLocationSettingsDialog(context: Context) {
    val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
    val dialog = builder.create()
    dialog.setCancelable(false)
    dialog.setTitle(context.getString(R.string.alert_dialog_location_enable_location))
    dialog.setMessage(context.getString(R.string.alert_dialog_location_disabled_warning))
    dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(
        R.string.alert_dialog_ok
    )){ _,_ ->
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        dialog.dismiss()
    }
    dialog.setIcon(R.drawable.ic_baseline_location_off_24)
    dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(
        R.string.alert_dialog_cancel
    )){ _,_ ->
        dialog.dismiss()
    }
    dialog.show()
}

fun Fragment.showNetworkSettingsDialog() {
    return DialogManager.networkSettingsDialog(requireContext(), object : DialogManager.Listener {
        override fun onClick(name: String?) {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
    })
}