package com.robotemilio.glovotest.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.ui.common.BaseFragment

class NoServerFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_no_server, container, false)
    }

}