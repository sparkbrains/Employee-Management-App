package sparkbrains.em.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import sparkbrains.em.navigation.routes.RootRoutes

@Composable
fun MainGraph(
    modifier: Modifier = Modifier,
    startDestination: RootRoutes = RootRoutes.AuthGraph,
) {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        authGraph(navController = navController)
    }

}