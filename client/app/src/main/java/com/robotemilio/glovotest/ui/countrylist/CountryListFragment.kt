package com.robotemilio.glovotest.ui.countrylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.extensions.getViewModel
import com.robotemilio.glovotest.extensions.observe
import com.robotemilio.glovotest.ui.common.BaseFragment
import com.robotemilio.glovotest.ui.map.MapsFragment.Companion.SELECTED_CITY
import kotlinx.android.synthetic.main.fragment_country_list.*

class CountryListFragment : BaseFragment() {

    private lateinit var listViewModel: CountriesViewModel
    private lateinit var countryListAdapter : CountryListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_country_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { countryListAdapter = CountryListAdapter(it) }
        initViews()
        initViewModel()

        listViewModel.getCountries()
    }

    private fun initViews() {
        countries_expandable_list.apply {
            setAdapter(countryListAdapter)
            setOnChildClickListener { parent, view, groupPosition, childPosition, id ->
                activity?.let {
                    val city = countryListAdapter.data[groupPosition].cities[childPosition]
                    Navigation.findNavController(it, R.id.nav_host_fragment)
                        .navigate(R.id.showCountryInMap, bundleOf(SELECTED_CITY to city))
                }

                true
            }
        }
    }

    private fun initViewModel() {
        listViewModel = getViewModel(activity as AppCompatActivity, viewModelFactory)

        observe(listViewModel.countries, ::onCountriesReceived)
    }

    private fun onCountriesReceived(countries: List<CountriesViewModel.CountryUiItem>?) {
        countries?.let { countryListAdapter.data = countries }
    }
}