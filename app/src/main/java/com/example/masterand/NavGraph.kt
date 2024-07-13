package com.example.masterand

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.FloatAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = "first_screen",
        enterTransition = {
            fadeIn( animationSpec = tween(1000, 0, easing = LinearOutSlowInEasing)
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(500, 100, easing = EaseIn)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(1000, 0, easing = LinearOutSlowInEasing)) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(500, 100, easing = EaseOut)
            )
        }
        ){

        composable(route = Screen.First.route){
            ProfileScreenInitial(navController = navController)
        }
        composable(route = "${Screen.Second.route}/{login}/{email}/{image}/{colorNumber}") {
            val colorNumber = it.arguments?.getString("colorNumber")
            val login = it.arguments?.getString("login")
            val email = it.arguments?.getString("email")
            val image = it.arguments?.getString("image")
            val temp = null
            profileCard(navController = navController,
                profile = tempProfile(login = login!!, description = email!!, image = if (image != "null") temp else null),
                colorNumber = colorNumber!!)
        }
        composable(route = "${Screen.Third.route}/{colorNumber}"){
            val colorNumber = it.arguments?.getString("colorNumber")
            GameScreen(navController = navController, colorNumber = colorNumber!!)
        }
        composable(route ="${Screen.Fourth.route}/{score}"){
            val score = it.arguments?.getInt("score")
            ResultScreenInitial(navController = navController, highScore = score!!)
        }
    }
}