package sparkbrains.em.navigation.routes

import kotlinx.serialization.Serializable

/**
 * Authentication routes are defined here
 **/
sealed interface AuthRoutes {

    @Serializable
    data object Login : AuthRoutes

    @Serializable
    data object ForgetPassword : AuthRoutes
}