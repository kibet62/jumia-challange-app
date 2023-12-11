package com.example.jumiachallange.navigation

abstract class RouterImpl<NavHostController>(
    private val navHostController: NavHostController,
    private val startDestination: String = ROUTE_SEARCH,
    override var saveState: Boolean,
    override var launchSingleTop: () -> Unit,
    override var restoreState: Boolean
) : Router {

    override fun goDetails(arg: Any?) {
        navigate(Screen.Details)
    }
    override fun goResults(arg: Any?) {
        navigate(Screen.Search)
    }

    abstract fun navigate(screen: Screen.Search)

    override fun goSearch() {
        navigate(Screen.Search, removeFromHistory = true, singleTop = true)
    }

    override fun goBack() {
        navHostController.apply {
            navigateUp()
            navigate(startDestination) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }



    override fun goSplash() {
        navigate(Screen.Splash, true)
    }

    abstract fun navigate(splash: Screen.Splash, b: Boolean)


    private fun navigate(
        screen: Screen.Details,
        removeFromHistory: Boolean = false,
        singleTop: Boolean = false
    ) {
        navHostController.apply {
            navigate(screen.route) {
                if (removeFromHistory) {
                    if (singleTop) {
                        popUpTo(Screen.Search.route)
                    } else {
                        popUpTo(0) {
                            saveState = false
                        }
                    }

                } else {
                    restoreState = true
                }
                launchSingleTop = singleTop
            }
        }
    }


}