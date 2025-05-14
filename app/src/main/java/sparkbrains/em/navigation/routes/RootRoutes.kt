package sparkbrains.em.navigation.routes

import kotlinx.serialization.Serializable

/**
 * Root routes are defined here
 **/
sealed interface RootRoutes {

    @Serializable
    data object AuthGraph: RootRoutes

    @Serializable
    data object DashBoardGraph: RootRoutes
}