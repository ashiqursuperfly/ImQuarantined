package com.imquarantined.ui.base

import androidx.lifecycle.ViewModel
import com.stylinecollection.opflex.util.api.ApiClient
import io.reactivex.disposables.CompositeDisposable

/* Created by ashiq.buet16 **/

abstract class BaseViewModel: ViewModel() {

    val mDisposable: CompositeDisposable = CompositeDisposable()
    val mCommonApiService by lazy { ApiClient.createCommonApiService() }
   
    override fun onCleared() {

        mDisposable.dispose()

        super.onCleared()
    }
}