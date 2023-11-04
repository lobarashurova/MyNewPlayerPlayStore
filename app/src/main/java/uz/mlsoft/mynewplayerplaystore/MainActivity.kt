package uz.mlsoft.mynewplayerplaystore

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import uz.mlsoft.mynewplayerplaystore.navigation.NavigationHandler
import uz.mlsoft.mynewplayerplaystore.presentation.splash.SplashScreen
import uz.mlsoft.mynewplayerplaystore.ui.theme.MyNewPlayerPlayStoreTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNewPlayerPlayStoreTheme() {
                // A surface container using the 'background' color from the theme
                val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    rememberPermissionState(permission = Manifest.permission.READ_MEDIA_AUDIO)
                } else {
                    rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(key1 = lifecycleOwner) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_CREATE) {
                            permissionState.launchPermissionRequest()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }
                if (permissionState.status.isGranted) {
                    Navigator(SplashScreen()) { navigator ->
                        LaunchedEffect(key1 = navigator) {
                            navigationHandler.uiNavigator.onEach {
                                it(navigator)
                            }.collect()
                        }
                        CurrentScreen()
                    }
                }
            }
        }
    }
}
