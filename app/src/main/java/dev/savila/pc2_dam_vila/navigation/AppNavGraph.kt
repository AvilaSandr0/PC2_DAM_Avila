package dev.savila.pc2_dam_vila.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.savila.pc2_dam_vila.Conversion.ConversionScreen
import dev.savila.pc2_dam_vila.auth.LoginScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("conversor") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("conversor") {
            ConversionScreen()
        }
        composable("conversor") {
            ConversionScreen()
        }
    }
}
