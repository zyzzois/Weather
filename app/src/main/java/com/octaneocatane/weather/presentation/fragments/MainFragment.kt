package com.octaneocatane.weather.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import com.octaneocatane.api.presentation.ViewPager2Adapter
import com.octaneocatane.weather.R
import com.octaneocatane.weather.databinding.FragmentMainBinding
import com.octaneocatane.weather.presentation.DialogManager
import com.octaneocatane.weather.presentation.MainViewModel
import com.squareup.picasso.Picasso

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding = null")

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private val viewPagerAdapter by lazy {
        ViewPager2Adapter(activity as AppCompatActivity, fragmentList)
    }

    private val fragmentList = listOf(HoursFragment.newInstance(), DaysFragment.newInstance())
    private val tabList = listOf("Hours", "Days")

    private lateinit var pLauncher: ActivityResultLauncher<String>

    private val fLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    //private lateinit var fLocationClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //checkPermission()
        init()
        updateCurrentCard()
        //getLocation()
    }

    private fun init() = with(binding) {
        viewPager2.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2){
                tab, pos -> tab.text = tabList[pos]
        }.attach()

        buttonSyncLocation.setOnClickListener {
            tabLayout.selectTab(tabLayout.getTabAt(0))
        }

        binding.buttonTypeCity.setOnClickListener {
            DialogManager.searchByNameDialog(requireContext(), object : DialogManager.Listener{
                override fun onClick(name: String?) {
                    name?.let { /*it1 -> requestWeatherData(it1)*/ }
                }
            })
        }
    }

    private fun updateCurrentCard() = with(binding) {
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            tvCity.text = it.city
            tvCurrentTemp.text = it.currentTemp
            tvCurrentCondition.text = it.conditionText
            tvLastUpdated.text = it.time
            val tempText = "${it.maxTemp} - max | min - ${it.minTemp}"
            tvMaxMinTemp.text = tempText
            Picasso.get().load("https:" + it.conditionIcon).into(imWeather)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}