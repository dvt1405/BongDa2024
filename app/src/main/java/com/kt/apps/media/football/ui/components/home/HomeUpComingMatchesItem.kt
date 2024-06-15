package com.kt.apps.media.football.ui.components.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

sealed interface ImagePainter {
    open class DrawableRes(val drawableRes: Int) : ImagePainter
    class Url(val url: String) : ImagePainter

    companion object : DrawableRes(R.drawable.home_live_item_2) {
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeUpComingMatchesItem(
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    homePainter: ImagePainter = ImagePainter,
    awayPainter: ImagePainter = ImagePainter,
    title: String = stringResource(id = R.string.home_title),
    time: String = stringResource(id = R.string.home_description),
    score: String = stringResource(id = R.string.home_score)
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(24.dp)
            )
            .background(
                color = androidx.compose.ui.graphics.Color(0xFFF0F0F0)
            )
            .combinedClickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = if (homePainter is ImagePainter.Url) {
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(homePainter.url)
                        .memoryCacheKey(MemoryCache.Key(homePainter.url))
                        .networkCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .crossfade(true)
                        .build()
                )
            } else {
                painterResource(id = (homePainter as ImagePainter.DrawableRes).drawableRes)
            },
            contentDescription = stringResource(id = R.string.home_title),
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(24.dp)
                )
                .size(64.dp)
        )

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .basicMarquee()
            )
            if (score.isNotEmpty()) {
                Text(
                    text = score,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Text(
                text = time,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            )
        }

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
            contentDescription = stringResource(id = R.string.home_title),
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(24.dp)
                )
                .size(64.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    HomeUpComingMatchesItem()
}

@Preview(showBackground = true)
@Composable
fun PreviewList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(1000) {
            HomeUpComingMatchesItem()
        }
    }
}

