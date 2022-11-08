package com.octaneocatane.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.octaneocatane.weather.R
import com.octaneocatane.weather.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            setContentView(binding.root)

        }
    }
}