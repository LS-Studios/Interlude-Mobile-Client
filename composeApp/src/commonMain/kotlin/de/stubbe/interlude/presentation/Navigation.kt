package de.stubbe.interlude.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.stubbe.interlude.domain.model.Route
import de.stubbe.interlude.domain.model.getRouteTabs
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.util.getNavRoute
import de.stubbe.interlude.presentation.components.BottomBar
import de.stubbe.interlude.presentation.components.TopBar
import de.stubbe.interlude.presentation.screens.ConverterScreen
import de.stubbe.interlude.presentation.screens.HistoryScreen
import de.stubbe.interlude.presentation.screens.SettingsScreen
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.converter
import interlude.composeapp.generated.resources.history
import interlude.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.getNavRoute() ?: Route.Converter

    val tabs = getRouteTabs()

    val converterText = stringResource(Res.string.converter)
    val historyText = stringResource(Res.string.history)
    val settingsText = stringResource(Res.string.settings)

    val currentScreenTitle = remember(currentRoute) {
        when (currentRoute) {
            is Route.Converter -> converterText
            is Route.History -> historyText
            is Route.Settings -> settingsText
        }
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