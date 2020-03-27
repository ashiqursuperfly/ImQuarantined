package com.imquarantined.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import com.imquarantined.data.Const
import com.imquarantined.ui.base.BaseViewModel
import com.imquarantined.util.helper.FirebaseAuthUtil
import com.imquarantined.util.helper.PrefUtil

class HomeViewModel : BaseViewModel() {

    val mLoginLiveData = MutableLiveData<Boolean>()

    fun login(idToken: String) {
        PrefUtil.set(Const.PrefProp.LOGIN_TOKEN, idToken)
        //TODO:
        // make api call, the token and verify
        // set pref only after backend verifies the user
        mLoginLiveData.postValue(true)

    }

    fun signOut() {
        PrefUtil.set(Const.PrefProp.LOGIN_TOKEN, Const.PrefProp.ACTION_DELETE)
        FirebaseAuthUtil.signOut()
    }


}
