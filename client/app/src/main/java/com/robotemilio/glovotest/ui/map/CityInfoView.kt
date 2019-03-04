package com.robotemilio.glovotest.ui.map

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.domain.model.City
import kotlinx.android.synthetic.main.widget_info_component.view.*

class CityInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        inflate(context, R.layout.widget_info_component, this)
    }

    fun setData(city : City) {
        city_name.text = context.getString(R.string.city_info_title, city.name, city.code)
        city_info.text = context.getString(R.string.city_info_line_1, city.timeZone, city.currency)
        city_country.text = context.getString(R.string.city_info_line_2, city.countryCode, city.languageCode)
    }

}