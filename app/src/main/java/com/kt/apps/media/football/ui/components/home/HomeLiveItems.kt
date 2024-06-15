package com.kt.apps.media.football.ui.components.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kt.apps.media.football.R
import com.kt.apps.media.football.ui.theme.textColor
import kotlin.random.Random


internal val backgroundBlur: Array<Int> = arrayOf(
    R.drawable.home_live_item,
    R.drawable.home_live_item_3,
    R.drawable.match_item_background,
    R.drawable.match_item_background_2,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeLiveItem(
    onClick: () -> Unit = {},
    imagePainter: ImagePainter = ImagePainter,
    awayPainter: ImagePainter = ImagePainter,
    name: String = "Real Madrid vs Barcelona",
    blurRes: Int = backgroundBlur[Random.nextInt(0, backgroundBlur.size)]
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .clip(shape = RoundedCornerShape(24.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = MaterialTheme.colorScheme.onSurface,
                ), // Change ripple color here
                onClick = onClick
            )
            .background(
                Color(0xFFF0F0F0),
                RoundedCornerShape(24.dp)
            ),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .blur(10.dp),
                painter = painterResource(
                    id = blurRes
                ),
                contentDescription = ""
            )
            Box(
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .padding(8.dp),
            ) {
                Image(
                    painter = if (imagePainter is ImagePainter.Url) {
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(imagePainter.url)
                                .memoryCacheKey(MemoryCache.Key(imagePainter.url))
                                .networkCachePolicy(CachePolicy.ENABLED)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .crossfade(true)
                                .build()
                        )
                    } else {
                        painterResource(id = (imagePainter as ImagePainter.DrawableRes).drawableRes)
                    },
                    contentDescription = "",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(24.dp))
                        .size(80.dp, 80.dp)
                )
                Image(
                    painter = if (awayPainter is ImagePainter.Url) {
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(awayPainter.url)
                                .memoryCacheKey(MemoryCache.Key(awayPainter.url))
                                .networkCachePolicy(CachePolicy.ENABLED)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .crossfade(true)
                                .build()
                        )
                    } else {
                        painterResource(id = (awayPainter as ImagePainter.DrawableRes).drawableRes)
                    },
                    contentDescription = "",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(24.dp))
                        .size(80.dp, 80.dp)
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .background(
                        Color(0xFFCDF2CE).copy(alpha = 0.4f),
                        RoundedCornerShape(24.dp)
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Text(
                    text = "VS",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    style = TextStyle(
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                    ),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Text(
            text = name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee()
                .padding(
                    vertical = 12.dp,
                    horizontal = 4.dp
                ),
            style = TextStyle(
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            ),
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeLiveItemPreview() {
    HomeLiveItem()
}

@Preview(showBackground = true)
@Composable
fun HomeLiveItemListPreview() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(10) {
            HomeLiveItem()
        }
    }
}

