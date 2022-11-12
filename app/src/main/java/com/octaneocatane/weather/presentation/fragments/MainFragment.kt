package com.octaneocatane.weather.presentation.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.octaneocatane.weather.R
import com.octaneocatane.weather.WeatherApplication
import com.octaneocatane.weather.databinding.FragmentMainBinding
import com.octaneocatane.weather.presentation.DialogManager
import com.octaneocatane.weather.presentation.MainViewModel
import com.octaneocatane.weather.presentation.ViewModelFactory
import com.octaneocatane.weather.presentation.ViewPager2Adapter
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
        get() = _binding ?: throw RuntimeException(BINDING_EXCEPTION_MESSAGE)

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
        startShimmer()
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
        return if (!locationEnabled) {
            Snackbar.make(binding.root, LOCATION_DISABLED_WARNING, Snackbar.LENGTH_SHORT).show()
            false
        } else {
            Snackbar.make(binding.root, LOADING_DATA_TEXT, Snackbar.LENGTH_SHORT).show()
            true
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
                Toast.makeText(activity, "$PERMISSION_STATUS_TEXT is $it", Toast.LENGTH_LONG).show()
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
            startShimmer()
            checkLocation()
            stopShimmer()
        }

        buttonTypeCity.setOnClickListener {
            /*
            DialogManager.searchCityCustomDialog(requireContext(), object : DialogManager.Listener {
                override fun onClick(name: String?) {
                    name?.let {
                        viewModel.loadData(it)
                    }
                }
            })
            */
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
            tvMaxMinTemp.text = requireContext().getString(R.string.max_min_texts, it.maxTemp, it.minTemp)
            Picasso.get().load(HTTPS + it.conditionIcon).into(imWeather)
            stopShimmer()
        }
    }

    private fun startShimmer() {
        with(binding) {
            viewPager2.visibility = View.INVISIBLE
            cardView.visibility = View.INVISIBLE
            shimmer.startShimmerAnimation()
            shimmerCard.startShimmerAnimation()
        }
    }
    private fun stopShimmer() {
        with(binding) {
            shimmer.stopShimmerAnimation()
            shimmerCard.stopShimmerAnimation()
            shimmer.visibility = View.GONE
            shimmerCard.visibility = View.INVISIBLE
            viewPager2.visibility = View.VISIBLE
            cardView.visibility = View.VISIBLE
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
        private const val HTTPS = "https:"
        private const val BINDING_EXCEPTION_MESSAGE = "FragmentMainBinding = null"
        private const val LOCATION_DISABLED_WARNING = "Location disabled!"
        private const val LOADING_DATA_TEXT = "Loading data..."
        private const val PERMISSION_STATUS_TEXT = "Permission is"
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}