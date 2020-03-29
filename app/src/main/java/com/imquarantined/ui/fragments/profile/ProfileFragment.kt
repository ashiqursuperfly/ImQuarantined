package com.imquarantined.ui.fragments.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.imquarantined.R
import com.imquarantined.ui.base.BaseFragment
import com.imquarantined.util.helper.DialogUtil
import com.imquarantined.util.helper.Toaster.showToast
import com.imquarantined.util.helper.load
import kotlinx.android.synthetic.main.fragment_profile.*

/* Created by ashiq.buet16 **/
class ProfileFragment : BaseFragment() {

    private val mProfileViewModel: ProfileViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun afterOnViewCreated() {

        observeData()
        DialogUtil.showLoader(requireContext())
        mProfileViewModel.loadProfile()
    }

    private fun observeData(){
        mProfileViewModel.mProfileResponseLiveData.observe(this, Observer {
            DialogUtil.hideLoader()

            if(it == null || !it.isSuccess){
                showToast("Error loading Profile: ${it.message}")
                return@Observer
            }

            //TODO:populate views with data
            img_user_photo.load(it.data.imageUrl.trim())
            tv_days_quarantined.text = it.data.daysQuarantined.toString()
            tv_total_pts.text = it.data.points.toString()
            tv_user_name.text= it.data.userName
            tv_current_streak.text= it.data.currentStreak.toString()
            tv_highest_streak.text= it.data.highestStreak.toString()
            tv_user_email.text=""


        })
    }
}
