package com.octaneocatane.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.octaneocatane.weather.R
import com.octaneocatane.weather.databinding.ActivityMainBinding
import com.octaneocatane.weather.presentation.fragments.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)
        viewModel.loadData("London")
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            setContentView(binding.root)
            supportFragmentManager
                .beginTransaction().replace(R.id.placeHolder, MainFragment.newInstance())
                .commit()
        }
    }
}