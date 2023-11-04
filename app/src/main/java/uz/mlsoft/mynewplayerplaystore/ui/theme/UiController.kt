package uz.mlsoft.mynewplayerplaystore.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetUiController(
    statusBarColor:Color = MaterialTheme.colorScheme.primary,
    navigationBarColor:Color = Color.Black,
    isDarkIconEnable:Boolean = false,
    isStatusBarVisible:Boolean = true,
    isNavigationBarVisible:Boolean = true,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(statusBarColor)
    systemUiController.statusBarDarkContentEnabled = isDarkIconEnable
    systemUiController.setNavigationBarColor(navigationBarColor)
    systemUiController.isStatusBarVisible = isStatusBarVisible
    systemUiController.isNavigationBarVisible = isNavigationBarVisible

}