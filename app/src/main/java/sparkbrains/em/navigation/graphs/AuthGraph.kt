package sparkbrains.em.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import sparkbrains.em.features.auth.login.LoginScreen
import sparkbrains.em.navigation.routes.AuthRoutes
import sparkbrains.em.navigation.routes.RootRoutes

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<RootRoutes.AuthGraph>(startDestination = AuthRoutes.Login) {
        composable<AuthRoutes.Login> {
            LoginScreen(navController)
        }
        composable<AuthRoutes.ForgetPassword> {
//            ForgetPasswordScreen(navController)
        }
    }
}