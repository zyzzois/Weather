package com.octaneocatane.weather.presentation.fragments

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.octaneocatane.weather.R
import com.octaneocatane.weather.databinding.FragmentMainBinding
import com.octaneocatane.weather.presentation.DialogManager
import com.octaneocatane.weather.presentation.MainViewModel
import com.octaneocatane.weather.presentation.ViewModelFactory
import com.octaneocatane.weather.presentation.ViewPager2Adapter
import com.octaneocatane.weather.utils.*
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
        checkPermissions()
        init()
        updateCurrentCard()
    }

    /*
    private fun checkNetworkConnection() {
        with(viewModel) {
            checkNetworkConnection(requireActivity())
            if (networkConnectionEnabled.value == false)
                showNetworkSettingsDialog()
        }
    }*/

    private fun checkLocation() {
        with(viewModel) {
            checkNetworkConnection(requireActivity())
            checkLocationConnection(requireActivity())
            if (locationEnabled.value == true) {
                showSnackbar(binding.root, LOADING_DATA_TEXT)

                getCurrentLocation(requireContext())
                /*loadData(currentLocationCoordinates.toString())*/
            } else {
                showSnackbar(binding.root, LOCATION_DISABLED_WARNING)
                showLocationSettingsDialog()
            }
        }
    }

    private fun checkPermissions() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()) {
                showToast(requireActivity(), status = it)
            }
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun init() {
        with (binding){
            viewPager2.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
                tab.text = tabList[pos]
            }.attach()
            buttonsClickListeners()
        }
    }

    private fun updateCurrentCard() {
        with (binding) {
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
    }

    private fun buttonsClickListeners() {
        with (binding) {
            buttonSyncLocation.setOnClickListener {
                tabLayout.selectTab(tabLayout.getTabAt(0))
                startShimmer()
                checkLocation()
                stopShimmer()
            }
            buttonTypeCity.setOnClickListener {
                DialogManager.searchByNameDialog(requireContext(), object : DialogManager.Listener {
                    override fun onClick(name: String?) {
                        name?.let {
                            viewModel.loadData(it)
                        }
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
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
        const val LOADING_DATA_TEXT = "Loading data..."
        const val PERMISSION_STATUS_TEXT = "Permission is"
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}