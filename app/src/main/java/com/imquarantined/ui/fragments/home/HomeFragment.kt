package com.imquarantined.ui.fragments.home

import androidx.fragment.app.viewModels
import com.imquarantined.R
import com.imquarantined.ui.base.BaseFragment

class HomeFragment : BaseFragment() {

    private val mHomeViewModel: HomeViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun afterOnViewCreated() {
        observeData()
        setToolBarData()
    }

    private fun observeData() {
        TODO("Not yet implemented")
    }

    private fun setToolBarData() {
    }


}
