package com.octaneocatane.weather.presentation

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.FrameLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.octaneocatane.weather.R

object DialogManager {
    fun locationSettingsDialog(context: Context, listener: Listener){
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
        val dialog = builder.create()
        dialog.setTitle("Enable location?")
        dialog.setMessage("Location disabled, do you want enable location?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){ _,_ ->
            listener.onClick(null)
            dialog.dismiss()
        }

        dialog.setIcon(R.drawable.ic_baseline_location_off_24)
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel"){ _,_ ->
            dialog.dismiss()
        }
        dialog.show()

    }
    fun searchByNameDialog(context: Context, listener: Listener){
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
        //val edName = R.layout.input_city_layout
        val edName = TextInputEditText(context)
        builder.setView(edName)
        val dialog = builder.create()
        dialog.setTitle("City name:")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){ _,_ ->
            listener.onClick(edName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel"){ _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }
    interface Listener{
        fun onClick(name: String?)
    }
}