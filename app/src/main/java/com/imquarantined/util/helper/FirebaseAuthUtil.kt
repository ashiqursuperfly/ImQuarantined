package com.imquarantined.util.helper

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.imquarantined.R


object FirebaseAuthUtil {

    private val mAuth = FirebaseAuth.getInstance()

    private var providers : ArrayList<AuthUI.IdpConfig>? = null

    fun init(){
//        val whitelistedCountries = arrayListOf("+880","bd")
//        providers = arrayListOf(PhoneBuilder()
//            .setWhitelistedCountries(whitelistedCountries)
//            .build())
        providers = arrayListOf(
            AuthUI.IdpConfig.FacebookBuilder().build()
            )

    }

    fun getIntent(): Intent? {
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setTheme(R.style.LoginTheme)
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(providers!!)
            .build()
    }

    fun getUser() : FirebaseUser?{
        return mAuth.currentUser
    }

    fun signOut() {
        mAuth.signOut()
    }

}