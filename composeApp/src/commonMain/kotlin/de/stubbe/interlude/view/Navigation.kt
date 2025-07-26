package de.stubbe.interlude.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.stubbe.interlude.model.Route
import de.stubbe.interlude.model.getRouteTabs
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.util.getNavRoute
import de.stubbe.interlude.view.components.BottomBar
import de.stubbe.interlude.view.components.TopBar
import de.stubbe.interlude.view.screens.ConverterScreen
import de.stubbe.interlude.view.screens.HistoryScreen
import de.stubbe.interlude.view.screens.SettingsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.getNavRoute() ?: Route.Converter

    val tabs = remember { getRouteTabs() }

    val currentScreenTitle = remember(currentRoute) {
        tabs.find { it.route == currentRoute }?.title ?: ""
    }

    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar =  {
            TopBar(
                title = currentScreenTitle
            )
        },
        bottomBar = {
            BottomBar(
                tabs = tabs,
                currentRoute = currentRoute,
                onTabSelected = { newRoute ->
                    if (currentRoute != newRoute) {
                        navController.navigate(newRoute) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        },
        containerColor = Colors.Background
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.Converter,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<Route.Converter> {
                ConverterScreen()
            }
            composable<Route.History> {
                HistoryScreen()
            }

            composable<Route.Settings> {
                SettingsScreen()
            }
        }
    }
}