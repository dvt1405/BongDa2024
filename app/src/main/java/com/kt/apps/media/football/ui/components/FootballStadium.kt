package com.kt.apps.media.football.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kt.apps.media.football.R

internal const val footballHorizontalRatio = 120f / 75
internal const val footballVerticalRatio = 75f / 120
internal const val footballHorizontalWidth = 120f
internal const val footballHorizontalHeight = 75f
@Composable
fun FootballStadium(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    content: @Composable () -> Unit = {}
) {
    val aspectRatio = if (orientation == Orientation.Horizontal) {
        footballHorizontalRatio
    } else {
        footballVerticalRatio
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .paint(
                painterResource(
                    id = if (orientation == Orientation.Horizontal) {
                        R.drawable.stadium
                    } else {
                        R.drawable.stadium_vertical
                    }
                ),
                contentScale = ContentScale.FillBounds,
            )
            .drawBehind {
                val goalKeeperWidth = if (orientation == Orientation.Horizontal) {
                    size.width * 5.5f / 120
                } else {
                    size.width * 18.3f / 75
                }
                val goalKeeperHeight = if (orientation == Orientation.Horizontal) {
                    size.height * 18.3f / 75
                } else {
                    size.height * 5.5f / 120
                }
                val height1650 = if (orientation == Orientation.Horizontal) {
                    size.height * 40.3f / 75
                } else {
                    size.height * 16.5f / 120
                }
                val width1650 = if (orientation == Orientation.Horizontal) {
                    size.width * 16.5f / 120
                } else {
                    size.width * 40.3f / 75
                }
                val arcSize = if (orientation == Orientation.Horizontal) {
                    Size(size.width * 9.15f / 120 * 2, size.width * 9.15f / 120 * 2)
                } else {
                    Size(size.height * 9.15f / 120 * 2, size.height * 9.15f / 120 * 2)
                }
                val goalKeeperSize = Size(
                    width = goalKeeperWidth,
                    height = goalKeeperHeight
                )
                val size1650 = Size(
                    width = width1650,
                    height = height1650
                )
                //=== Draw home
                drawHomeGoalKeeper(
                    orientation = orientation,
                    goalKeeperSize = goalKeeperSize,
                    size1650 = size1650,
                    arcSize = arcSize
                )
                //=== Draw center
                drawCenter(
                    orientation = orientation
                )
                //=== Draw away
                drawGoalKeeperAway(
                    orientation = orientation,
                    goalKeeperSize = goalKeeperSize,
                    size1650 = size1650,
                    arcSize = arcSize
                )
            }
    ) {
        content()
    }
}

fun DrawScope.drawCenter(orientation: Orientation) {
    if (orientation == Orientation.Horizontal) {
        drawLine(
            color = Color.White,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
            strokeWidth = 1.dp.toPx()
        )
    } else {
        drawLine(
            color = Color.White,
            start = Offset(0f, size.height/2),
            end = Offset(size.width, size.height/2),
            strokeWidth = 1.dp.toPx()
        )
    }

    drawCircle(
        color = Color.White,
        center = Offset(size.width / 2, size.height / 2),
        radius = 4.dp.toPx(),
    )
    drawCircle(
        color = Color.White,
        center = Offset(size.width / 2, size.height / 2),
        radius = size.width * 9.15f / 120 * 2,
        style = Stroke(width = 1.dp.toPx())
    )
}

fun DrawScope.drawHomeGoalKeeper(
    orientation: Orientation = Orientation.Horizontal,
    goalKeeperSize: Size,
    size1650: Size,
    arcSize: Size
) {
    val offsetGoalKeeper = if (orientation == Orientation.Horizontal) {
        Offset(0f, (size.height - goalKeeperSize.height) / 2)
    } else {
        Offset((size.width - goalKeeperSize.width) / 2, 0f)
    }
    drawRect(
        color = Color.White,
        topLeft = offsetGoalKeeper,
        size = goalKeeperSize,
        style = Stroke(width = 1.dp.toPx())
    )
    val offset1650 = if (orientation == Orientation.Horizontal) {
        Offset(0f, (size.height - size1650.height) / 2)
    } else {
        Offset((size.width - size1650.width) / 2, 0f)
    }
    drawRect(
        color = Color.White,
        topLeft = offset1650,
        size = size1650,
        style = Stroke(width = 1.dp.toPx())
    )
    val centerOffset11m = if (orientation == Orientation.Horizontal) {
        Offset(size.width * 11f / 120, size.height / 2)
    } else {
        Offset(size.width / 2, size.height * 11f / 120)
    }
    drawCircle(
        color = Color.White,
        center = centerOffset11m,
        radius = 2.dp.toPx(),
    )
    if (orientation == Orientation.Horizontal) {
        drawArc(
            color = Color.White,
            startAngle = -53f,
            sweepAngle = 105f,
            useCenter = false,
            topLeft = Offset(centerOffset11m.x - arcSize.width / 2, centerOffset11m.y - arcSize.width / 2),
            size = arcSize,
            style = Stroke(width = 1.dp.toPx())
        )
    } else {
        drawArc(
            color = Color.White,
            startAngle = 37f,
            sweepAngle = 106f,
            useCenter = false,
            topLeft = Offset(centerOffset11m.x - arcSize.width / 2, centerOffset11m.y - arcSize.width / 2),
            size = arcSize,
            style = Stroke(width = 1.dp.toPx())
        )
    }
}

fun DrawScope.drawGoalKeeperAway(
    orientation: Orientation,
    goalKeeperSize: Size,
    size1650: Size,
    arcSize: Size
) {
    val offsetGoalKeeper = if (orientation == Orientation.Horizontal) {
        Offset(size.width - goalKeeperSize.width,
            (size.height - goalKeeperSize.height) / 2
        )
    } else {
        Offset((size.width - goalKeeperSize.width) / 2,
            size.height - goalKeeperSize.height
        )
    }
    drawRect(
        color = Color.White,
        topLeft = offsetGoalKeeper,
        size = goalKeeperSize,
        style = Stroke(width = 1.dp.toPx())
    )
    val offset1650 = if (orientation == Orientation.Horizontal) {
        Offset(size.width - size1650.width,
            (size.height - size1650.height) / 2
        )
    } else {
        Offset((size.width - size1650.width) / 2,
            size.height - size1650.height
        )
    }
    drawRect(
        color = Color.White,
        topLeft = offset1650,
        size = size1650,
        style = Stroke(width = 1.dp.toPx())
    )

    val centerOffset11mRight = if (orientation == Orientation.Horizontal) {
        Offset(size.width - size.width * 11f / 120, size.height / 2)
    } else {
        Offset(size.width / 2, size.height - size.height * 11f / 120)
    }

    drawCircle(
        color = Color.White,
        center = centerOffset11mRight,
        radius = 2.dp.toPx(),
    )

    if (orientation == Orientation.Horizontal) {
        drawArc(
            color = Color.White,
            startAngle = -127f,
            sweepAngle = -105f,
            useCenter = false,
            topLeft = Offset(
                centerOffset11mRight.x - arcSize.width / 2,
                centerOffset11mRight.y - arcSize.width / 2
            ),
            size = arcSize,
            style = Stroke(width = 1.dp.toPx())
        )
    } else {
        drawArc(
            color = Color.White,
            startAngle = -37f,
            sweepAngle = -106f,
            useCenter = false,
            topLeft = Offset(
                centerOffset11mRight.x - arcSize.width / 2,
                centerOffset11mRight.y - arcSize.width / 2
            ),
            size = arcSize,
            style = Stroke(width = 1.dp.toPx())
        )
    }
}

@Preview
@Composable
fun FootballStadiumPreview() {
    FootballStadium() {
        Row(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("GK")
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("LB")
                    Text("CB")
                    Text("CB")
                    Text("RB")
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("CDM")
                    Text("RCM")
                    Text("LCM")
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 8.dp),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("RW")
                    Text("ST")
                    Text("LW")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("RW")
                    Text("ST")
                    Text("LW")
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("CDM")
                    Text("RCM")
                    Text("LCM")
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("LB")
                    Text("CB")
                    Text("CB")
                    Text("RB")
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("GK")
                }
            }
        }
    }
}

@Preview
@Composable
fun FootballStadiumVerticalPreview() {
    FootballStadium(
        orientation = Orientation.Vertical
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("GK")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("LB")
                    Text("CB")
                    Text("CB")
                    Text("RB")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("CDM")
                    Text("RCM")
                    Text("LCM")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("RW")
                    Text("ST")
                    Text("LW")
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("ST")
                    Text("ST")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("RM")
                    Text("RCM")
                    Text("LCM")
                    Text("LM")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("LB")
                    Text("CB")
                    Text("CB")
                    Text("RB")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                ) {
                    Text("GK",
                        style = TextStyle(color = Color.Yellow)
                    )
                }
            }
        }
    }
}