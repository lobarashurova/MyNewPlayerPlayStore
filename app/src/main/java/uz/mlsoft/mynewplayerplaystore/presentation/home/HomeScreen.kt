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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.mlsoft.mynewplayerplaystore.R
import uz.mlsoft.mynewplayerplaystore.ui.components.MusicItem
import uz.mlsoft.mynewplayerplaystore.ui.components.PlayerIconItem
import uz.mlsoft.mynewplayerplaystore.ui.components.WrapperColumn
import uz.mlsoft.mynewplayerplaystore.ui.theme.MyNewPlayerPlayStoreTheme
import uz.mlsoft.mynewplayerplaystore.utils.getAlbumArt
import uz.mlsoft.mynewplayerplaystore.utils.myTimber

class HomeScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val screenModel: HomeContract.ScreenModel = getScreenModel<HomeViewModel>()
        screenModel.onEventDispatcher(HomeContract.Event.LoadAudioData)
        HomeScreenContent(screenModel.collectAsState(), screenModel::onEventDispatcher)
    }
}

@Composable
fun HomeScreenContent(
    uiState: State<HomeContract.UIState>,
    onEventDispatcher: (HomeContract.Event) -> Unit
) {

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
                myTimber("size screen list:${uiState.value.audioList}")
            }

        }
        Card(
            shape = CircleShape.copy(all = CornerSize(32.dp)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(75.dp)
        ) {
            WrapperColumn {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                        .height(2.dp),
                    progress = uiState.value.progress / 100f,
                )
                Row(Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier
                            .size(64.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .align(Alignment.CenterVertically),
                        bitmap = getAlbumArt(
                            LocalContext.current,
                            uiState.value.currentMusicModel.uri
                        ).asImageBitmap(),
                        contentDescription = "Audio image",
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = uiState.value.currentMusicModel.title,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Clip,
                            maxLines = 1,
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = uiState.value.currentMusicModel.artist,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.titleSmall,
                            overflow = TextOverflow.Clip,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = null,
                        modifier = Modifier.clickable { onEventDispatcher(HomeContract.Event.OnPrev) })
                    Spacer(modifier = Modifier.size(8.dp))
                    PlayerIconItem(icon = if (uiState.value.isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow) {
                        onEventDispatcher(HomeContract.Event.OnStart)
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(imageVector = Icons.Default.SkipNext, contentDescription = null,
                        modifier = Modifier.clickable { onEventDispatcher(HomeContract.Event.OnNext) })
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
