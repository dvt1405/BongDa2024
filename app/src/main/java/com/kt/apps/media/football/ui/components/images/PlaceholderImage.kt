package com.kt.apps.media.football.ui.components.images

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kt.apps.media.football.ui.components.home.ImagePainter


@Composable
fun ImageUrl(
    drawableRes: ImagePainter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Image(
        painter = if (drawableRes is ImagePainter.Url) {
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(drawableRes.url)
                    .memoryCacheKey(MemoryCache.Key(drawableRes.url))
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build()
            )
        } else {
            painterResource(id = (drawableRes as ImagePainter.DrawableRes).drawableRes)
        },
        contentDescription = contentDescription,
        modifier = modifier
    )
}