package com.example.jumiachallange.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.example.jumiachallange.ui.theme.screens.details.DetailsScreen
import com.example.jumiachallange.ui.theme.screens.results.ResultsScreen
import com.example.jumiachallange.ui.theme.screens.search.SearchScreen
import com.example.jumiachallange.ui.theme.screens.splash.SplashScreen

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun SetupNavGraph(
    router: Router,
    navController: NavHostController,
    resultsViewModel: ViewModel = hiltViewModel(),
    detailsViewModel: ViewModel = hiltViewModel(),
    configurationsViewModel: ViewModel = hiltViewModel(),
) {

    val startDestination = remember { mutableStateOf(Screen.Splash.route) }
    LaunchedEffect(startDestination) {
        if (startDestination.value == Screen.Search.route) {
            router.goSearch()
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination.value
    ) {
        composable(route = Screen.Splash.route){
            SplashScreen(
                launchSearchScreen = {
                    startDestination.value=  Screen.Search.route
                }
            )

        }
        composable(route = Screen.Search.route){
            SearchScreen(navController = navController)
        }
        composable(
            route = Screen.Results.route,
            arguments = listOf(navArgument(SEARCH_ARGUMENT_KEY){
                type = NavType.StringType
            })
        ){

            ResultsScreen(
                navController = navController, resultsViewModel,
                configurationsViewModel = configurationsViewModel,
                searchQuery = it.arguments?.getString(SEARCH_ARGUMENT_KEY).toString() )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(SKU_ARGUMENT_KEY){
                type = NavType.StringType
            })
        ){

            DetailsScreen(
                navController = navController,
                detailsViewModel =  detailsViewModel,
                sku = it.arguments?.getString(SKU_ARGUMENT_KEY).toString(),
                configurationsViewModel = configurationsViewModel)
        }
    }
}