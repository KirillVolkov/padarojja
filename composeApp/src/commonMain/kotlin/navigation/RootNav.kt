package navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import screen.main.MainScreen
import screen.MapScreen

@Composable
fun RootNav() {
    val navController = rememberNavController()

    BottomSheetRoot { type ->
        NavHostMain(
            navController = navController,
            onNavigate = { routeName ->
                navigateTo(routeName, navController)
            },
            bottomSheetType = type
        )
    }
}

@Composable
fun NavHostMain(
    navController: NavHostController = rememberNavController(),
    onNavigate: (rootName: String) -> Unit,
    bottomSheetType: (BottomSheetType) -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination

    Scaffold(
//        topBar = {
//            val title = getTitle(currentScreen)
//            TopBar(
//                title = title,
//                canNavigateBack = true,
//                navigateUp = { navController.navigateUp() }
//            )
//        },
//        bottomBar = {
//            BottomNavigationBar(navController)
//        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainBottomBarScreen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
//            composable(route = AppScreen.MainScreen.route) {
//                MainScreen(onNavigate = onNavigate)
//            }
            composable(route = MainBottomBarScreen.Home.route) {
                MainScreen(onNavigate = onNavigate, bottomSheetType = bottomSheetType)
            }
            composable(route = MainBottomBarScreen.Map.route) {
                MapScreen(onNavigate = onNavigate)
            }
        }
    }
}

fun getTitle(currentScreen: NavDestination?): String {
    return when (currentScreen?.route) {
        AppScreen.MainScreen.route -> {
            "Main"
        }

        else -> ""
    }
}

fun navigateTo(
    routeName: String,
    navController: NavController
) {
    when (routeName) {
//        AppConstants.BACK_CLICK_ROUTE -> {
//            navController.popBackStack()
//        }

        else -> {
            navController.navigate(routeName)
        }
    }
}

@Composable
fun TopBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back_button"
                    )
                }
            }
        }
    )
}

@Composable
fun AppBottomNavigationBar(
    modifier: Modifier = Modifier,
    show: Boolean,
    content: @Composable (RowScope.() -> Unit),
) {
    Surface(
        color = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        if (show) {
            Column {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = Color.Black.copy(alpha = 0.2f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val homeItem = MainBottomBarScreen.Home
    val reelsItem = MainBottomBarScreen.Map

    val screens = listOf(homeItem, reelsItem)

    AppBottomNavigationBar(show = navController.shouldShowBottomBar) {
        screens.forEach { item ->
            AppBottomNavigationBarItem(
                icon = item.defaultIcon,
                label = item.title,
                onClick = {
                    navigateBottomBar(navController, item.route)
                },
                selected = navController.currentBackStackEntry?.destination?.route == item.route
            )
        }
    }
}

@Composable
fun RowScope.AppBottomNavigationBarItem(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    label: String,
    onClick: () -> Unit,
    selected: Boolean,
) {
    Column(
        modifier = modifier
            .weight(1f)
            .clickable(
                onClick = onClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = icon.toString(),
            contentScale = ContentScale.Crop,
            colorFilter = if (selected) {
                ColorFilter.tint(MaterialTheme.colors.primary)
            } else {
                ColorFilter.tint(MaterialTheme.colors.secondary)
            },
            modifier = modifier.then(
                Modifier.clickable {
                    onClick()
                }.size(24.dp)
            )
        )

        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            fontWeight = if (selected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.secondary
            }
        )
    }
}
