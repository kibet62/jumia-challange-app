package com.example.jumiachallange.navigation

interface Router {
    var saveState: Boolean
    var launchSingleTop: () -> Unit
    var restoreState: Boolean

    fun goDetails(arg: Any?)
    fun goSplash()
    fun goResults(arg: Any?)
    fun goSearch()
    fun goBack()
    fun popUpTo(i: Int, function: () -> Unit)
    fun popUpTo(i: String)
    fun navigateUp()
    fun navigate(screen: String, singleTop: () -> Unit)
    fun navigate(search: Screen.Search, removeFromHistory: Boolean, singleTop: Boolean)
}