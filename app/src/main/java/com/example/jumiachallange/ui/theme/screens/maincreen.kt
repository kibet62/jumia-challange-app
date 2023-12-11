package com.example.jumiachallange.ui.theme.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.example.jumiachallange.navigation.SetupNavGraph


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalPagingApi::class, ExperimentalCoilApi::class)
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(finish: () -> Unit) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route ?: Screen.Splash.route
    val router: Router = remember { RouterImpl(navController, route) }

    if (route == Screen.Search.route) {
        BackHandler {
            finish()
        }
    }

    SetupNavGraph(router = router , navController = navController )

}