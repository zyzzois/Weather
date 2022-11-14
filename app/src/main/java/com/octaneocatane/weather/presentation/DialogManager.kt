package com.octaneocatane.weather.presentation

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.octaneocatane.weather.R
import javax.inject.Inject

object DialogManager {

    fun locationSettingsDialog(context: Context, listener: Listener){
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setTitle(context.getString(R.string.alert_dialog_location_enable_location))
        dialog.setMessage(context.getString(R.string.alert_dialog_location_disabled_warning))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(
            R.string.alert_dialog_ok
        )){ _,_ ->
            listener.onClick(null)
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

    fun networkSettingsDialog(context: Context, listener: Listener){
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setTitle(context.getString(R.string.alert_dialog_location_enable_network))
        dialog.setMessage(context.getString(R.string.alert_dialog_network_disabled_warning))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(
            R.string.alert_dialog_ok
        )){ _,_ ->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setIcon(R.drawable.ic_wifi_off)
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(
            R.string.alert_dialog_cancel
        )){ _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun searchByNameDialog(context: Context, listener: Listener){
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
        val edName = TextInputEditText(context)
        builder.setView(edName)
        val dialog = builder.create()
        dialog.setTitle(context.getString(R.string.alert_dialog_city_name))
        dialog.setIcon(R.drawable.ic_city_seach)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(
            R.string.alert_dialog_ok
        )){ _,_ ->
            listener.onClick(edName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(
            R.string.alert_dialog_cancel
        )){ _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface Listener{
        fun onClick(name: String?)
    }
}