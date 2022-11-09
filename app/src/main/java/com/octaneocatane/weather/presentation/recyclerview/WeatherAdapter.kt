package com.octaneocatane.weather.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.octaneocatane.weather.databinding.ListItemBinding
import com.octaneocatane.weather.domain.WeatherEntity
import com.octaneocatane.weather.utils.Constants
import com.squareup.picasso.Picasso

class WeatherAdapter : ListAdapter<WeatherEntity, WeatherHolder>(WeatherItemDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {

        val weatherItem = getItem(position)

        with(holder.binding) {

            val itemIsHour = weatherItem.currentTemp != Constants.UNDEFINED_CURRENT_TEMP
            val itemIsDay = weatherItem.currentTemp == Constants.UNDEFINED_CURRENT_TEMP

            if (itemIsDay) {
                val tempText = "${weatherItem.maxTemp} | ${weatherItem.minTemp}"
                tvTemp.text = tempText
            }

            if (itemIsHour) {
                tvTemp.text = weatherItem.currentTemp
            }

            Picasso.get().load("https:" + weatherItem.conditionIcon).into(imHourItemCondition)
            tvCondition.text = weatherItem.conditionText
            tvDate.text = weatherItem.time
        }
    }
}


