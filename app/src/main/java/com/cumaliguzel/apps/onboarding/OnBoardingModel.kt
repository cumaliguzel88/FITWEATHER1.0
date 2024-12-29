package com.cumaliguzel.apps.onboarding

import android.content.Context
import com.cumaliguzel.apps.R

sealed class OnBoardingModel(
    val animationResId: Int?,
    val title: (Context) -> String,
    val description: (Context) -> String
) {
    data object FirstPages : OnBoardingModel(
        animationResId = R.raw.first_animation,
        title = { context -> context.getString(R.string.onboarding_first_title) },
        description = { context -> context.getString(R.string.onboarding_first_desc) }
    )
    data object SecondPages : OnBoardingModel(
        animationResId = R.raw.second_animation,
        title = { context -> context.getString(R.string.onboarding_second_title) },
        description = { context -> context.getString(R.string.onboarding_second_desc) }
    )
    data object ThirdPages : OnBoardingModel(
        animationResId = R.raw.third_animation,
        title = { context -> context.getString(R.string.onboarding_third_title) },
        description = { context -> context.getString(R.string.onboarding_third_desc) }
    )
    data object ForthPages : OnBoardingModel(
        animationResId = R.raw.comment_animation_girl,
        title = { context -> context.getString(R.string.onboarding_forth_title) },
        description = { context -> context.getString(R.string.onboarding_forth_desc) }
    )
    data object FifthPages : OnBoardingModel(
        animationResId = R.raw.lets_animation,
        title = { context -> context.getString(R.string.onboarding_fifth_title) },
        description = { context -> context.getString(R.string.onboarding_fifth_desc) }
    )
}
