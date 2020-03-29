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
    }

    private fun observeData(){
        mProfileViewModel.mProfileResponseLiveData.observe(this, Observer {
            DialogUtil.hideLoader()

            if(it == null || !it.isSuccess){
                showToast("Error loading Profile: ${it.message}")
                return@Observer
            }

            //TODO:populate views with data
            img_user_photo.load("https://graph.facebook.com/10216598066973550/picture")
            tv_days_quarantined.text = "321"
            tv_total_pts.text = "31231"
            tv_user_name.text="Ashiqur Rahman"
            tv_current_streak.text="3"
            tv_highest_streak.text="5"
            tv_user_email.text="ashiqur.buet16@gmail.com"



        })
    }
}
