package com.cumaliguzel.apps.onboarding

import android.content.Context
import androidx.navigation.NavController

class OnBoardingUtils( val context : Context) {
    fun isOnboardingCompleted(): Boolean {
        return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .getBoolean("completed", false)
    }

    fun setOnboardingCompleted() {
        context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("completed", true)
            .apply()

    }
}