package com.octaneocatane.weather.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.octaneocatane.weather.domain.WeatherEntity

object WeatherItemDiffCallBack : DiffUtil.ItemCallback<WeatherEntity>() {
    override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
        return oldItem == newItem
    }
}