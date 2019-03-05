package com.robotemilio.glovotest.ui.countrylist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.Country

class CountryListAdapter(private val context: Context) : BaseExpandableListAdapter() {

    var data: List<CountriesViewModel.CountryUiItem> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getGroup(position: Int): Country {
        return data[position].country
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
       return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val group = getGroup(groupPosition)
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.widget_list_group_view, null)
        }

        val groupTextView = convertView?.findViewById(R.id.list_group_text) as TextView
        groupTextView.text = group.name

        val drawable = if (isExpanded) {
            ContextCompat.getDrawable(context, R.drawable.ic_collapse)
        } else {
            ContextCompat.getDrawable(context, R.drawable.ic_expand)
        }
        groupTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

        return convertView
    }

    override fun getChildrenCount(position: Int): Int {
        return data[position].cities.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): City {
        return data[groupPosition].cities[childPosition]
    }

    override fun getGroupId(position: Int): Long {
        return position.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.widget_list_item_view, null)
        }

        val childTextView = convertView?.findViewById(R.id.list_item_text) as TextView
        childTextView.text = data[groupPosition].cities[childPosition].name
        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return data.size
    }

}