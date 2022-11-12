package com.octaneocatane.weather.presentation.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.octaneocatane.weather.R
import com.octaneocatane.weather.WeatherApplication
import com.octaneocatane.weather.databinding.FragmentHoursBinding
import com.octaneocatane.weather.presentation.DialogManager
import com.octaneocatane.weather.presentation.MainViewModel
import com.octaneocatane.weather.presentation.ViewModelFactory
import com.octaneocatane.weather.presentation.recyclerview.WeatherAdapter
import javax.inject.Inject

class HoursFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as WeatherApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
    }

    private var _binding: FragmentHoursBinding? = null
    private val binding: FragmentHoursBinding
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

    private fun customDialog() {
        fun searchCityCustomDialog(){
            val builder = MaterialAlertDialogBuilder(requireContext())
            val cl = requireActivity().layoutInflater.inflate(R.layout.custom_alert_dialog, null)
            builder.setView(cl)
            builder.show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BINDING_EXCEPTION_MESSAGE = "FragmentHoursBinding = null"

        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}