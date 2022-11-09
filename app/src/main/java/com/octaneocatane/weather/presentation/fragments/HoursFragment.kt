package com.octaneocatane.weather.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.octaneocatane.weather.R
import com.octaneocatane.weather.databinding.FragmentHoursBinding
import com.octaneocatane.weather.presentation.MainViewModel
import com.octaneocatane.weather.presentation.recyclerview.WeatherAdapter

class HoursFragment : Fragment() {
    private var _binding: FragmentHoursBinding? = null
    private val binding: FragmentHoursBinding
        get() = _binding ?: throw RuntimeException("FragmentHoursBinding = null")

    private lateinit var weatherAdapter: WeatherAdapter

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        viewModel.hoursList.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it)
        }
    }

    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        weatherAdapter = WeatherAdapter()
        rcView.adapter = weatherAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}