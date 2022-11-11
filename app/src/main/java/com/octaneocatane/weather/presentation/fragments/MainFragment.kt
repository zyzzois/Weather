package com.octaneocatane.weather.presentation.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.octaneocatane.weather.WeatherApplication
import com.octaneocatane.weather.presentation.ViewPager2Adapter
import com.octaneocatane.weather.databinding.FragmentMainBinding
import com.octaneocatane.weather.presentation.DialogManager
import com.octaneocatane.weather.presentation.MainViewModel
import com.octaneocatane.weather.presentation.ViewModelFactory
import com.octaneocatane.weather.utils.isPermissionGranted
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MainFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as WeatherApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
    }

    private val fLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding = null")

    private val viewPagerAdapter by lazy {
        ViewPager2Adapter(activity as AppCompatActivity, fragmentList)
    }

    private val fragmentList = listOf(HoursFragment.newInstance(), DaysFragment.newInstance())
    private val tabList = listOf(HOURS, DAYS)

    override fun onAttach(context: Context) {
        component.inject(this@MainFragment)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        updateCurrentCard()
    }

    private fun checkLocation() {
        if (isLocationEnabled()) {
            getLocation()
        } else {

            DialogManager.locationSettingsDialog(requireContext(), object : DialogManager.Listener {
                override fun onClick(name: String?) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })

        }
    }

    private fun isLocationEnabled(): Boolean {
        val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!locationEnabled) {
            //Toast.makeText(requireContext(), "Location disabled!", Toast.LENGTH_LONG).show()
            return false
        } else {
            Snackbar.make(binding.root, "Loading data...", Snackbar.LENGTH_LONG)
                .show()
            return true
        }
    }

    private fun getLocation() {
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener {
                val coordinates = "${it.result.latitude},${it.result.longitude}"
                viewModel.loadData(coordinates)
            }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()) {
                Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
            }
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun init() = with(binding) {
        viewPager2.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
            tab.text = tabList[pos]
        }.attach()

        buttonSyncLocation.setOnClickListener {
            tabLayout.selectTab(tabLayout.getTabAt(0))
            checkLocation()
        }

        binding.buttonTypeCity.setOnClickListener {
            DialogManager.searchByNameDialog(requireContext(), object : DialogManager.Listener {
                override fun onClick(name: String?) {
                    name?.let { viewModel.loadData(it) }
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
            tvMaxMinTemp.text = "${it.maxTemp} - max | min - ${it.minTemp}"
            Picasso.get().load("https:" + it.conditionIcon).into(imWeather)
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val HOURS = "hours"
        private const val DAYS = "days"
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}