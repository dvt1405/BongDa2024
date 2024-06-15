package com.kt.apps.media.football.ui.components.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kt.apps.media.football.R

@Composable
fun RankingRow(
    modifier: Modifier = Modifier,
    rank: String,
    teamName: String,
    played: String,
    won: String,
    drawn: String,
    lost: String,
    goalsFor: String,
    goalsAgainst: String,
    goalDifference: String,
    points: String,
    isFinalItem: Boolean = false,
    painter: Painter? = painterResource(id = R.drawable.logo_man_city),
    textStyle: TextStyle = TextStyle.Default,
    topRanking: IntRange = 1..4,
    nearTopRanking: IntRange = 5..5,
    bottomRanking: IntRange = 18..20
) {
    Row(
        modifier =
        if (isFinalItem) {
            modifier
                .height(30.dp)
                .drawBehind {
                    this.drawLine(
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        color = Color.Black
                            .copy(alpha = 0.08f),
                        strokeWidth = 1.dp.toPx()
                    )
                }
        } else {
            modifier.height(30.dp)
        }
            .drawBehind {
                this.drawLine(
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    color = if (rank.toIntOrNull() in topRanking) {
                        Color(0xFF5383EC)
                    } else if (rank.toIntOrNull() in nearTopRanking) {
                        Color(0xFFEA8237)
                    } else if (rank.toIntOrNull() in bottomRanking) {
                        Color(0xFFD85140)
                    } else {
                        Color(0xFFbdc1c6)
                    },
                    strokeWidth = 1.dp.toPx()
                )
            }
            .background(
                if (rank.toIntOrNull() in topRanking) {
                    Color(0xFF5383EC)
                } else if (rank.toIntOrNull() in nearTopRanking) {
                    Color(0xFFEA8237)
                } else if (rank.toIntOrNull() in bottomRanking) {
                    Color(0xFFD85140)
                } else {
                    Color.Transparent
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = rank,
                modifier = Modifier
                    .width(20.dp),
                textAlign = TextAlign.Center,
                style = textStyle
            )
            painter?.let {
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
            } ?: Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = teamName,
                maxLines = 1,
                style = textStyle,
            )
        }
        Text(
            text = played,
            modifier = Modifier
                .width(40.dp),
            textAlign = TextAlign.Center,
            style = textStyle,
        )
        Text(
            text = won,
            modifier = Modifier
                .width(40.dp),
            textAlign = TextAlign.Center,
            style = textStyle,
        )
        Text(
            text = drawn,
            modifier = Modifier
                .width(40.dp),
            textAlign = TextAlign.Center,
            style = textStyle,
        )
        Text(
            text = lost,
            modifier = Modifier
                .width(40.dp),
            textAlign = TextAlign.Center,
            style = textStyle,
        )
        Text(
            text = if (goalsAgainst.isNotEmpty()) {
                "$goalsFor:$goalsAgainst"
            } else {
                goalsFor
            },
            modifier = Modifier
                .width(40.dp),
            textAlign = TextAlign.Center,
            style = textStyle,
        )
        Text(
            text = points,
            modifier = Modifier
                .width(40.dp),
            textAlign = TextAlign.Center,
            style = textStyle.copy(
                fontWeight = FontWeight.SemiBold
            ),
        )
    }
}

@Composable
fun RankingHeaderRow() {
    RankingRow(
        rank = "#",
        teamName = "Club",
        played = "MP",
        won = "W",
        drawn = "D",
        lost = "L",
        goalsFor = "F",
        goalsAgainst = "A",
        goalDifference = "GD",
        points = "P",
        painter = null,
        modifier = Modifier
            .then(Modifier.background(Color(0xFF3A134F))),
        textStyle = TextStyle(
            color = Color.White
        )
    )
}

@Composable
@Preview
fun RankingHeaderRowPreview() {
    RankingHeaderRow()
}

@Composable
@Preview
fun RankingRowPreview() {
    val listTeam = listOf(
        "Manchester City",
        "Manchester United",
        "Liverpool",
        "Chelsea",
        "Leicester City",
        "Arsenal",
        "Tottenham Hotspur",
        "Everton",
        "West Ham United",
        "Leeds United",
        "Aston Villa",
        "Newcastle United",
        "Wolverhampton Wanderers",
        "Crystal Palace",
        "Southampton",
        "Brighton & Hove Albion",
        "Burnley",
        "Fulham",
        "West Bromwich Albion",
        "Sheffield United"
    )
    var count = 1
    Column(
        modifier = Modifier
            .padding(top = 96.dp)
    ) {
        RankingRow(
            rank = "#",
            teamName = "Club",
            played = "MP",
            won = "W",
            drawn = "D",
            lost = "L",
            goalsFor = "F",
            goalsAgainst = "A",
            goalDifference = "GD",
            points = "P",
            painter = null,
            modifier = Modifier
                .then(Modifier.background(Color(0xFF3A134F))),
            textStyle = TextStyle(
                color = Color.White
            )
        )
        for (i in listTeam) {
            RankingRow(
                rank = count++.toString(),
                teamName = i,
                played = "38",
                won = "27",
                drawn = "5",
                lost = "6",
                goalsFor = "83",
                goalsAgainst = "32",
                goalDifference = "51",
                points = "86",
                isFinalItem = count == listTeam.size + 1,
                modifier = if (count in 2..5 || count in 19..21) {
                    Modifier
                } else {
                    Modifier
                        .background(Color(0xFFF1F3F4))
                },
                textStyle = when (count) {
                    in 2..6 -> TextStyle.Default.copy(
                        color = Color.White
                    )

                    in 19..21 -> TextStyle.Default.copy(
                        color = Color(0xFFF1F3F4)
                    )

                    else -> TextStyle.Default
                }
            )
        }
    }
}