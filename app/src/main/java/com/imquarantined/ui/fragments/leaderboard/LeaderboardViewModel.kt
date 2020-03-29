package com.imquarantined.ui.fragments.leaderboard

import androidx.lifecycle.MutableLiveData
import com.imquarantined.data.api_response.LeaderboardResponse
import com.imquarantined.ui.base.BaseViewModel
import com.imquarantined.util.helper.Toaster
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


/* Created by ashiq.buet16 **/

class LeaderboardViewModel : BaseViewModel() {
    
    val mLeaderboardResponseLiveData = MutableLiveData<LeaderboardResponse>()
    
    fun loadLeaderboard(){
        mDisposable.add(
            mCommonApiService.getLeaderBoard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mLeaderboardResponseLiveData.postValue(it)
                    Timber.d("LeaderboardResponse: $it")
                }, {
                    mLeaderboardResponseLiveData.postValue(null)
                    Toaster.showToast("Error loading Leaderboards: ${it.message}")
                }
                )
        )
    }
    
}