package com.imquarantined.ui.fragments.leaderboard

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.imquarantined.R
import com.imquarantined.data.UserEntity
import com.imquarantined.ui.adapters.UsersAdapter
import com.imquarantined.ui.base.BaseFragment
import com.imquarantined.ui.callbacks.OnClickUserCallback
import com.imquarantined.util.helper.DialogUtil
import com.imquarantined.util.helper.Toaster
import com.imquarantined.util.helper.Toaster.showToast
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import timber.log.Timber

/* Created by ashiq.buet16 **/

class LeaderboardFragment : BaseFragment() {

    private val mLeaderboardViewModel: LeaderboardViewModel by viewModels()
    private lateinit var mUsersAdapter: UsersAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_leaderboard
    }

    override fun afterOnViewCreated() {
        observeData()
        initRecyclerView()
        DialogUtil.showLoader(requireContext())
        mLeaderboardViewModel.loadLeaderboard()
    }

    private fun observeData(){
        mLeaderboardViewModel.mLeaderboardResponseLiveData.observe(this, Observer {
            DialogUtil.hideLoader()

            if(it == null || !it.isSuccess){
                Toaster.showToast("Error loading Leaderboard: ${it?.message?:""}")
                return@Observer
            }

            mUsersAdapter.removeAll()
            mUsersAdapter.addItems(ArrayList(it.data.topUsers.toList()))

            //TODO: populate recyclerview
            Timber.i("LEADERBOARD")
            for (item in it.data.topUsers){
                Timber.d("$item")
            }

        })
    }

    private fun initRecyclerView() {
        mUsersAdapter = UsersAdapter()
        mUsersAdapter.setCallback(object : OnClickUserCallback {
            override fun onClickUser(user: UserEntity) {
                showToast(user.userName)
            }
        })

        rv_leaderboard.setHasFixedSize(true)

        val layoutManager = GridLayoutManager(
            context, 1, GridLayoutManager.VERTICAL, false
        )

        rv_leaderboard.layoutManager = layoutManager
        rv_leaderboard.adapter = mUsersAdapter

    }

}