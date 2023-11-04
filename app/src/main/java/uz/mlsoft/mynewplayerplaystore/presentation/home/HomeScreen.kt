package uz.mlsoft.mynewplayerplaystore.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.mlsoft.mynewplayerplaystore.R
import uz.mlsoft.mynewplayerplaystore.ui.components.MusicItem
import uz.mlsoft.mynewplayerplaystore.ui.theme.MyNewPlayerPlayStoreTheme
import uz.mlsoft.mynewplayerplaystore.ui.theme.colorPrimaryDark
import uz.mlsoft.mynewplayerplaystore.utils.getAlbumArt
import uz.mlsoft.mynewplayerplaystore.utils.myTimber

class HomeScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val screenModel: HomeContract.ScreenModel = getScreenModel<HomeViewModel>()

        HomeScreenContent(screenModel.collectAsState(), screenModel::onEventDispatcher)
    }
}

@Composable
fun HomeScreenContent(
    uiState: State<HomeContract.UIState>,
    onEventDispatcher: (HomeContract.Event) -> Unit
) {

    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .background(color = Color.White)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Your musics",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 15.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.group_48),
                    contentDescription = "", modifier = Modifier
                        .padding(end = 10.dp, top = 15.dp)
                        .size(25.dp)
                        .align(CenterVertically)

                )
                Image(
                    painter = painterResource(id = R.drawable.group_21),
                    contentDescription = "", modifier = Modifier
                        .padding(end = 15.dp, top = 15.dp, start = 10.dp)
                        .size(25.dp)
                        .align(CenterVertically)
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(uiState.value.audioList) { index, audio ->
                    MusicItem(model = audio) {
                        onEventDispatcher(HomeContract.Event.OnItemClick(index))
                    }

                }
            }

        }
        Card(
            modifier = Modifier
                .padding(bottom = 15.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                Image(
                    bitmap = getAlbumArt(
                        context,
                        uiState.value.currentMusicModel.uri
                    ).asImageBitmap(),

                    contentDescription = "",
                    modifier = Modifier
                        .size(65.dp)
                        .clip(RoundedCornerShape(15.dp))
                )
                myTimber("uri screen:${uiState.value.currentMusicModel.uri}")
                Column() {
                    Text(
                        text = uiState.value.currentMusicModel.artist,
                        fontWeight = FontWeight(500),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 10.dp, start = 20.dp)
                    )

                    Text(
                        text = uiState.value.currentMusicModel.displayName,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.align(CenterVertically)) {
                    Image(
                        painter = painterResource(id = R.drawable.before),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            colorPrimaryDark
                        ), modifier = Modifier
                            .padding(end = 10.dp)
                            .size(30.dp)
                            .align(CenterVertically)
                            .clickable {

                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            colorPrimaryDark

                        ), modifier = Modifier
                            .size(30.dp)
                            .padding(end = 10.dp)
                            .align(CenterVertically)
                            .clickable { }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.after),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            colorPrimaryDark

                        ), modifier = Modifier
                            .padding(end = 10.dp)
                            .size(30.dp)
                            .clickable { }
                    )
                }

            }
        }

    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun HomeScreenPrev() {
    MyNewPlayerPlayStoreTheme {
        HomeScreenContent(mutableStateOf(HomeContract.UIState())) {}
    }
}
