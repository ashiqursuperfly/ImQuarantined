package com.imquarantined.ui.fragments.profile

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.imquarantined.BuildConfig
import com.imquarantined.R
import com.imquarantined.ui.base.BaseFragment
import com.imquarantined.util.helper.AndroidUtil
import com.imquarantined.util.helper.DialogUtil
import com.imquarantined.util.helper.PermissionsUtil
import com.imquarantined.util.helper.Toaster.showToast
import com.imquarantined.util.helper.load
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_profile.*


/* Created by ashiq.buet16 **/
class ProfileFragment : BaseFragment() {

    private val mProfileViewModel: ProfileViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun afterOnViewCreated() {

        observeData()
        setClickListener(btn_take_ss)
        DialogUtil.showLoader(requireContext())
        mProfileViewModel.loadProfile()
    }

    private fun observeData(){
        mProfileViewModel.mProfileResponseLiveData.observe(this, Observer {
            DialogUtil.hideLoader()

            if(it == null || !it.isSuccess){
                showToast("Error loading Profile: ${it?.message?:""}")
                return@Observer
            }

            img_user_photo.load(it.data.imageUrl.trim())
            tv_days_quarantined.text = it.data.daysQuarantined.toString()
            tv_total_pts.text = it.data.points.toString()
            tv_user_name.text= it.data.userName
            tv_current_streak.text= it.data.currentStreak.toString()
            tv_highest_streak.text= it.data.highestStreak.toString()
            tv_user_email.text=""
        })
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        if(v!=null){
            if(PermissionsUtil.isPermissionAllowed(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                AndroidUtil.storeImage(AndroidUtil.takescreenshotOfRootView(v), requireContext())
            }
/*            else if (PermissionsUtil.isPermissionDenied(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) && mProfileViewModel.isUserLoggedIn()){
                showToast("Cannot Share, Without Storage Permission. Please Enable Storage Permission")
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                    )
                )
            }
*/
            else {
                PermissionsUtil.requestPermission(requireContext(),
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                            AndroidUtil.storeImage(AndroidUtil.takescreenshotOfRootView(v), requireContext())
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?
                        ) {
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                            showToast("Cannot Share, Without Storage Permission. Please Enable Storage Permission")
                            startActivity(
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                                )
                            )
                        }

                    }
                )

            }

        }
    }
}
