package com.imquarantined.ui.fragments.profile

import androidx.lifecycle.MutableLiveData
import com.imquarantined.data.api_response.ProfileResponse
import com.imquarantined.ui.base.BaseViewModel
import com.imquarantined.util.helper.Toaster.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/* Created by ashiq.buet16 **/

class ProfileViewModel : BaseViewModel() {

    val mProfileResponseLiveData = MutableLiveData<ProfileResponse>()

    fun loadProfile(){
        mDisposable.add(
            mCommonApiService.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mProfileResponseLiveData.postValue(it)
                    Timber.d("ProfileResponse: $it")
                }, {
                    mProfileResponseLiveData.postValue(null)
                    showToast("Error loading Profile: ${it.message}")
                }
                )
        )

    }

}