package com.kt.apps.media.football.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    spread: Dp = 0.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius > 0.dp) {
                val blurRadiusPixel = blurRadius.toPx()
                frameworkPaint.setShadowLayer(
                    blurRadiusPixel,
                    offsetX.toPx(),
                    offsetY.toPx(),
                    color.toArgb()
                )
            }
            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                leftPixel,
                topPixel,
                rightPixel,
                bottomPixel,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
        }
    }
)