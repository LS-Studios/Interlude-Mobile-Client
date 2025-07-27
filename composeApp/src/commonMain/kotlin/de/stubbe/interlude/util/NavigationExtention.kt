package de.stubbe.interlude.util

import androidx.navigation.NavBackStackEntry
import de.stubbe.interlude.domain.model.Route

/**
 * Extension functions to work with navigation routes in the app.
 * These functions help to retrieve the current navigation route from a NavBackStackEntry
 * and check if a specific route is present in the back stack.
 */
fun NavBackStackEntry.getNavRoute(): Route? {
    val currentRoute = this.destination.route ?: return null
    return when {
        currentRoute.contains(Route.Converter::class.simpleName.toString(), ignoreCase = true) -> Route.Converter
        currentRoute.contains(Route.History::class.simpleName.toString(), ignoreCase = true) -> Route.History
        currentRoute.contains(Route.Settings::class.simpleName.toString(), ignoreCase = true) -> Route.Settings
        else -> null
    }
}

/**
 * Checks if the current NavBackStackEntry corresponds to a specific navigation route.
 *
 * @param navRoute The navigation route to check against.
 * @return True if the current entry matches the specified route, false otherwise.
 */
fun NavBackStackEntry.isNavRoute(navRoute: Route?): Boolean {
    if (navRoute == null) return false
    return this.getNavRoute()!!::class.qualifiedName == navRoute::class.qualifiedName
}

/**
 * Checks if a list of routes contains a specific navigation route.
 *
 * @param navRoute The navigation route to check for.
 * @return True if the list contains the specified route, false otherwise.
 */
fun List<Any>.containsRoute(navRoute: Route?): Boolean {
    if (navRoute == null) return false
    return this.any {
        it::class.qualifiedName?.replace(".Companion", "") ==
                navRoute::class.qualifiedName?.replace(".Companion", "")
    }
}