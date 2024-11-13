package navigation

import androidx.navigation.NavController
import org.jetbrains.compose.resources.DrawableResource
import vandrouka.composeapp.generated.resources.Res
import vandrouka.composeapp.generated.resources.ic_map
import vandrouka.composeapp.generated.resources.ic_search

sealed class AppScreen(val route: String) {
    data object MainScreen : AppScreen("main_screen")
}

sealed class MainBottomBarScreen(
    val route: String,
    var title: String,
    val defaultIcon: DrawableResource
) {
    data object Home : MainBottomBarScreen(
        route = "Home",
        title = "Home",
        defaultIcon = Res.drawable.ic_search
    )

    data object Map : MainBottomBarScreen(
        route = "Map",
        title = "Map",
        defaultIcon = Res.drawable.ic_map
    )
}

internal val NavController.shouldShowBottomBar
    get() = when (this.currentBackStackEntry?.destination?.route) {
        MainBottomBarScreen.Home.route,
        MainBottomBarScreen.Map.route,
        -> true

        else -> false
    }


internal fun navigateBottomBar(navController: NavController, destination: String) {
    navController.navigate(destination) {
        navController.graph.startDestinationRoute?.let { route ->
            popUpTo(MainBottomBarScreen.Home.route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}
