package com.cumaliguzel.apps.onboarding

//This sealed class purpose :
//We  create as many objects as the number of pages it will consist of
import com.cumaliguzel.apps.R


sealed class OnBoardingModel(
    val animationResId: Int?, // Lottie dosyası için kaynak ID'si
    val title: String,
    val description: String
) {
    data object FirstPages : OnBoardingModel(
        animationResId = R.raw.first_animation, // Lottie dosya ID
        title = "What will you wear today?",
        description = "Do you sometimes wonder what to wear? Let us help you decide and make your day stress-free!"
    )
    data object SecondPages : OnBoardingModel(
        animationResId = R.raw.second_animation,
        title = "Check the Weather, Pick Your Outfit",
        description = "Our app suggests the perfect outfit combinations based on the weather, so you can stay stylish and comfortable every day!"
    )
    data object ThirdPages : OnBoardingModel(
        animationResId = R.raw.third_animation,
        title = "We've Left a Link for You",
        description = "Easily access the outfits you like and similar items with a simple link, making shopping for your favorite pieces hassle-free"
    )
    data object ForthPages : OnBoardingModel(
        animationResId = R.raw.comment_animation_girl,
        title ="Add Your Thoughts",
        description = "Share your opinions and feedback seamlessly with our comment feature"
    )
    data object FifthPages : OnBoardingModel(
        animationResId = R.raw.lets_animation,
        title = "Let’s Start",
        description = "You’re ready to explore and start! Let's go"
    )
}
