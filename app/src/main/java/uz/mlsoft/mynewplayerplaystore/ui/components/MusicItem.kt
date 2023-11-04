package uz.mlsoft.mynewplayerplaystore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import uz.mlsoft.mynewplayerplaystore.R
import uz.mlsoft.mynewplayerplaystore.data.model.MusicModel
import uz.mlsoft.mynewplayerplaystore.utils.getAlbumArt
import uz.mlsoft.mynewplayerplaystore.utils.timeStampToDuration


@Composable
fun MusicItem(model: MusicModel, onClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .fillMaxWidth()
            .height(72.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x059DC014))
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Image(
                    bitmap = getAlbumArt(context, model.uri).asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(58.dp)
                        .align(Alignment.CenterVertically)
                        .clip(RoundedCornerShape(15.dp)),
                )
                Column(modifier = Modifier.padding(start = 15.dp).align(Alignment.CenterVertically)) {
                    Text(
                        text = model.artist,
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontFamily = FontFamily(Font(R.font.monserrat)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        )
                    )

                    Text(
                        text = model.title,
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontFamily = FontFamily(Font(R.font.monserrat)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        )
                    )
                }


            }
            Text(
                text = timeStampToDuration(model.duration.toLong()),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MusicItemPrev() {
    MusicItem(MusicModel("".toUri(), "My music", 0L, "Adriel", "My data", 12, "Jingos")) {}
}
