package com.imquarantined.ui.fragments.home

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.imquarantined.R
import com.imquarantined.data.Const
import com.imquarantined.ui.base.BaseFragment
import com.imquarantined.util.helper.DialogUtil
import com.imquarantined.util.helper.FirebaseAuthUtil
import com.imquarantined.util.helper.PrefUtil
import com.imquarantined.util.helper.Toaster.showToast
import timber.log.Timber


class HomeFragment : BaseFragment() {

    val mHomeViewModel: HomeViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun afterOnViewCreated() {
        observeData()
        setToolBarData()
        initFirebaseAuthUI()
    }

    private fun observeData() {
        mHomeViewModel.mLoginLiveData.observe(this, Observer {
            if(it){
                showToast("Setup IdToken in sharedPref\n ${PrefUtil.get(Const.PrefProp.LOGIN_TOKEN,"-1")}")
                DialogUtil.hideLoader()
            }
        })

    }

    private fun setToolBarData() {}

    private fun initFirebaseAuthUI() {
        if(FirebaseAuthUtil.getUser() == null) startActivityForResult(FirebaseAuthUtil.getIntent(), Const.RequestCode.FIREBASE_AUTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.RequestCode.FIREBASE_AUTH) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                DialogUtil.showLoader(requireContext())

                FirebaseAuthUtil.getUser()?.getIdToken(true)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val idToken: String = task.result?.token ?:""
                            Timber.i("Token$idToken")
                            mHomeViewModel.loggin(idToken)
                        } else {
                            showToast(task.exception?.message.toString())
                            Timber.e(task.exception?.message.toString())
                            mHomeViewModel.signOut()
                            DialogUtil.hideLoader()
                            activity?.finish()
                        }
                    }
            } else {
                if(response == null){
                    showToast("Login Cancelled")
                }
                showToast("Login Failed ${response?.error?.message?:""}")
                activity?.finish()
            }
        }
    }



}