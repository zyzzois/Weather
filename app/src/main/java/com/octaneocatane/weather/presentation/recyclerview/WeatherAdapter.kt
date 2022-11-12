package com.octaneocatane.weather.presentation.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.octaneocatane.weather.databinding.ListItemBinding
import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.utils.Constants
import com.squareup.picasso.Picasso

class WeatherAdapter(
    private val listener: Listener
) : ListAdapter<WeatherEntity, WeatherHolder>(WeatherItemDiffCallBack), View.OnClickListener {

    override fun onClick(v: View?) {
        val day = v?.tag as WeatherEntity
        listener.obChooseDay(day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        binding.root.setOnClickListener(this)

        return WeatherHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {

        val weatherItem = getItem(position)

        with(holder.binding) {
            root.tag =  weatherItem

            val itemIsHour = weatherItem.currentTemp != Constants.UNDEFINED_CURRENT_TEMP
            val itemIsDay = weatherItem.currentTemp == Constants.UNDEFINED_CURRENT_TEMP

            if (itemIsDay) {
                val tempText = "${weatherItem.maxTemp} | ${weatherItem.minTemp}"
                tvTemp.text = tempText
            }

            if (itemIsHour) {
                tvTemp.text = weatherItem.currentTemp
            }

            Picasso.get().load(HTTPS + weatherItem.conditionIcon).into(imHourItemCondition)
            tvCondition.text = weatherItem.conditionText
            tvDate.text = weatherItem.time
        }
    }

    interface Listener {
        fun obChooseDay(day: WeatherEntity)
    }

    companion object {
        private const val HTTPS = "https:"
    }



}


