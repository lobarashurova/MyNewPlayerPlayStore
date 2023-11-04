package uz.mlsoft.mynewplayerplaystore.presentation.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import uz.mlsoft.mynewplayerplaystore.R
import uz.mlsoft.mynewplayerplaystore.ui.theme.MyNewPlayerPlayStoreTheme
import uz.mlsoft.mynewplayerplaystore.ui.theme.colorPrimary
import uz.mlsoft.mynewplayerplaystore.ui.theme.colorPrimaryDark

class SplashScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        getScreenModel<SplashViewModel>()
        SplashScreenContent()
        Log.d("TTT", "Content: ")
    }
}

@Composable
fun SplashScreenContent() {
    val gradient = Brush.linearGradient(
        0.0f to colorPrimary,
        500f to colorPrimaryDark,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.group_4),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Image(
                    painter = painterResource(id = R.drawable.music),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "App",
                    fontSize = 45.sp,
                    color = Color.White,
                    fontWeight = FontWeight(600),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )


            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPrev() {
    MyNewPlayerPlayStoreTheme {
        SplashScreenContent()
    }
}