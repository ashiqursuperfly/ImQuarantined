package com.imquarantined.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import com.imquarantined.data.Const
import com.imquarantined.data.api_response.HomeResponse
import com.imquarantined.ui.base.BaseViewModel
import com.imquarantined.util.helper.FirebaseAuthUtil
import com.imquarantined.util.helper.PrefUtil
import com.imquarantined.util.helper.Toaster.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class HomeViewModel : BaseViewModel() {

    val mLoginLiveData = MutableLiveData<Boolean>()
    val mHomeContentsLiveData = MutableLiveData<HomeResponse>()

    fun login(idToken: String) {
        mDisposable.add(
            mCommonApiService.login(idToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        PrefUtil.set(Const.PrefProp.LOGIN_TOKEN, idToken)
                        mLoginLiveData.postValue(true)
                        showToast("Welcome: ${it.data.userName}")
                        Timber.d("AuthResponse: ${it}")

                    } else {
                        mLoginLiveData.postValue(false)
                        showToast("Login Failed: ${it.message}")
                    }
                }, {
                    mLoginLiveData.postValue(false)
                    showToast("Login Failed: ${it.message}")
                }
                )
        )
    }

    fun loadHomeContents(){
        mDisposable.add(
            mCommonApiService.getHomeContents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mHomeContentsLiveData.postValue(it)
                    Timber.d("HomeResponse: $it")
                }, {
                    mHomeContentsLiveData.postValue(null)
                    showToast("Error loading Homecontents: ${it.message}")
                }
                )
        )

    }


    fun signOut() {
        PrefUtil.set(Const.PrefProp.LOGIN_TOKEN, Const.PrefProp.ACTION_DELETE)
        FirebaseAuthUtil.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        val token = PrefUtil.get(Const.PrefProp.LOGIN_TOKEN, "-1")
        return token != "-1"
    }


}
