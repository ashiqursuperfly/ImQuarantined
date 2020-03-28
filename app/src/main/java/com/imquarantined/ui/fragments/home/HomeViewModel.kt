package com.imquarantined.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import com.imquarantined.data.Const
import com.imquarantined.ui.base.BaseViewModel
import com.imquarantined.util.helper.FirebaseAuthUtil
import com.imquarantined.util.helper.PrefUtil
import com.imquarantined.util.helper.Toaster.showToast

class HomeViewModel : BaseViewModel() {

    var mLocationData = Pair(0.0,0.0)
    val mLoginLiveData = MutableLiveData<Boolean>()

    fun login(idToken: String) {
        PrefUtil.set(Const.PrefProp.LOGIN_TOKEN, idToken)
        //TODO:
        // make api call, the token and verify
        // set pref only after backend verifies the user
        mLoginLiveData.postValue(true)
        showToast("Welcome")

    }

    fun signOut() {
        PrefUtil.set(Const.PrefProp.LOGIN_TOKEN, Const.PrefProp.ACTION_DELETE)
        FirebaseAuthUtil.signOut()
    }


}
