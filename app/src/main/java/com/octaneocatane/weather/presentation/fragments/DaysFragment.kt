package com.octaneocatane.weather.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.octaneocatane.weather.app.WeatherApp
import com.octaneocatane.weather.databinding.FragmentDaysBinding
import com.octaneocatane.domain.WeatherEntity
import com.octaneocatane.weather.presentation.viewmodel.MainViewModel
import com.octaneocatane.weather.presentation.viewmodel.ViewModelFactory
import com.octaneocatane.weather.presentation.recyclerview.WeatherAdapter
import javax.inject.Inject

class DaysFragment : Fragment(), DataFragmentsInterface {

    private val component by lazy {
        (requireActivity().application as WeatherApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
    }

    private var _binding: FragmentDaysBinding? = null
    private val binding: FragmentDaysBinding
        get() = _binding ?: throw RuntimeException(BINDING_EXCEPTION_MESSAGE)

    private lateinit var weatherAdapter: WeatherAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
        weatherAdapter = WeatherAdapter(object : WeatherAdapter.Listener {
            override fun obChooseDay(day: WeatherEntity) {
                setDataToViewModel(day)
            }
        })
        rcViewDays.adapter = weatherAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BINDING_EXCEPTION_MESSAGE = "FragmentShopItemBinding = null"
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun setDataToViewModel(data: WeatherEntity) {
        Log.d("TAG", data.toString())
    }
}