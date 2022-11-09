package com.octaneocatane.weather.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.octaneocatane.weather.R
import com.octaneocatane.weather.databinding.FragmentDaysBinding
import com.octaneocatane.weather.presentation.MainViewModel
import com.octaneocatane.weather.presentation.recyclerview.WeatherAdapter

class DaysFragment : Fragment()
{
    private var _binding: FragmentDaysBinding? = null
    private val binding: FragmentDaysBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding = null")

    private lateinit var weatherAdapter: WeatherAdapter

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        viewModel.daysList.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it)
        }
    }

    private fun initRcView() = with(binding) {
        rcViewDays.layoutManager = LinearLayoutManager(activity)
        weatherAdapter = WeatherAdapter()
        rcViewDays.adapter = weatherAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}