package com.imquarantined.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.imquarantined.R
import com.imquarantined.data.Const
import com.imquarantined.ui.base.BaseActivity
import com.imquarantined.util.helper.DialogUtil
import com.imquarantined.util.helper.FirebaseAuthUtil
import com.imquarantined.util.helper.Toaster.showToast
import timber.log.Timber

class MainActivity : BaseActivity() {
    
    private lateinit var mNavController: NavController
    private val mMainViewModel: MainViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getToolbarId(): Int {
        return NO_TOOLBAR
    }

    override fun onClick(v: View?) {
        super.onClick(v)
    }

    override fun afterOnCreate() {
        observeData()
        initFirebaseAuthUI()
    }

    private fun observeData() {

        mMainViewModel.mLoginLiveData.observe(this, Observer {
            if(it){
                //TODO: route to loginFragment
            }
        })

    }

    private fun initFirebaseAuthUI() {
        FirebaseAuthUtil.init()
        if(FirebaseAuthUtil.getUser() != null){
            //TODO: route to mainFragment
        }
        startActivityForResult(FirebaseAuthUtil.getIntent(), Const.RequestCode.FIREBASE_AUTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.RequestCode.FIREBASE_AUTH) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                showToast("Welcome: ${user?.displayName}")
                DialogUtil.showLoader(this)

                FirebaseAuthUtil.getUser()?.getIdToken(true)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val idToken: String = task.result?.token ?:""
                            Timber.i("Token$idToken")
                            mMainViewModel.login(idToken)
                        } else {
                            showToast(task.exception?.message.toString())
                            Timber.e(task.exception?.message.toString())
                            mMainViewModel.signOut()
                            DialogUtil.hideLoader()
                            //TODO: exit app
                        }
                    }
            } else {
                if(response == null){
                    showToast("Login Cancelled")
                }
                showToast("Login Failed ${response?.error?.message?:""}")
                //TODO: exit app
            }
        }
    }


}
