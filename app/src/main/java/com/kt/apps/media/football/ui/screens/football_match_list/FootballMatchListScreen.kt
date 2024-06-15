package com.kt.apps.media.football.ui.screens.football_match_list

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kt.apps.media.football.MainViewModel
import com.kt.apps.media.football.R
import com.kt.apps.media.football.ui.components.home.HomeLiveItem
import com.kt.apps.media.football.ui.components.home.HomeUpComingMatchesItem
import com.kt.apps.media.football.ui.components.home.ImagePainter
import com.kt.apps.media.football.ui.components.search.SearchView
import com.kt.apps.media.football.ui.screens.FootballLiveNavScreens
import com.kt.apps.media.football.ui.screens.home.models.toFootballMatch
import com.kt.apps.media.football.ui.screens.player.PlayerViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun FootballMatchListTopBar(
    title: String = "Welcome to\nFootball Live App!"
) {
    TopAppBar(title = {
        Text(
            title,
            modifier = Modifier
                .padding(8.dp),
            style = MaterialTheme.typography.bodyLarge
                .copy(
                    fontWeight = FontWeight.Bold,
                    lineHeightStyle = LineHeightStyle.Default,
                    lineHeight = 20.sp,
                )
        )
    }, navigationIcon = {
        Image(
            painter = painterResource(id = R.drawable.home_logo),
            contentDescription = "",
            modifier = Modifier.clip(shape = CircleShape),
        )
    }, actions = {
        Icon(
            painter = painterResource(id = R.drawable.round_circle_notifications_24),
            contentDescription = "Notification",
            modifier = Modifier.combinedClickable(
                onClick = {}
            )
        )
    },
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp,
            top = 8.dp,
            bottom = 0.dp
        )
    )
}

@Composable
fun FootballMatchListScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    playerViewModel: PlayerViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    navHostController: NavHostController = rememberNavController()
) {

    val homeUIState = mainViewModel.homeUIState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchView(
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(key = "live_matches") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(
                            vertical = 12.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Live matches",
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                            .copy(
                                fontWeight = FontWeight.Bold,
                                lineHeightStyle = LineHeightStyle.Default,
                                lineHeight = 20.sp,
                            )
                    )
                    Row {
                        Text(
                            text = "Explore",
                            modifier = Modifier.padding(vertical = 8.dp),
                            style = MaterialTheme.typography.bodyLarge
                                .copy(
                                    fontWeight = FontWeight.Bold,
                                    lineHeightStyle = LineHeightStyle.Default,
                                    lineHeight = 20.sp,
                                ),
                        )
                    }
                }
            }
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(homeUIState.value.liveMatches.size, key = {
                        homeUIState.value.liveMatches[it].id
                    }) { index ->
                        val item = homeUIState.value.liveMatches[index]
                        if (index == 0) {
                            Spacer(modifier = Modifier.size(24.dp))
                        }
                        HomeLiveItem(
                            onClick = {
                                playerViewModel.getLinkForMatch(item.toFootballMatch())
                                navHostController.navigate(
                                    FootballLiveNavScreens.DETAIL_MATCH.route.replace(
                                        "{matchId}",
                                        item.id
                                    )
                                )
                            },
                            name = item.name,
                            imagePainter = ImagePainter.Url(
                                item.homeTeam.logo
                            ),
                            awayPainter = ImagePainter.Url(
                                item.awayTeam.logo
                            ),
                            blurRes = item.blurRes
                        )
                    }
                }
            }
            item(key = "all_matches") {
                Text(
                    text = "All Matches",
                    modifier = Modifier.padding(
                        vertical = 16.dp
                    ),
                    style = MaterialTheme.typography.bodyLarge
                        .copy(
                            fontWeight = FontWeight.Bold,
                            lineHeightStyle = LineHeightStyle.Default,
                            lineHeight = 20.sp,
                        )
                )
            }
            items(homeUIState.value.upcomingMatches.size, key = {
                homeUIState.value.upcomingMatches[it].id
            }) {
                val item = homeUIState.value.upcomingMatches.get(it)
                HomeUpComingMatchesItem(
                    onClick = {
                        playerViewModel.getLinkForMatch(item)
                        navHostController.navigate(
                            FootballLiveNavScreens.DETAIL_MATCH.route.replace(
                                "{matchId}",
                                item.id
                            )
                        )
                    },
                    title = item.name,
                    score = item.score.takeIf {
                        it.isNotEmpty()
                    } ?: item.liveStatus,
                    homePainter = ImagePainter.Url(
                        item.homeTeam.logo
                    ),
                    awayPainter = ImagePainter.Url(
                        item.awayTeam.logo
                    ),
                    time = item.hour
                )
            }
        }
    }
}

@Preview
@Composable
fun FootballMatchTopBarScreenPreview() {
    FootballMatchListTopBar()
}

@Preview
@Composable
fun FootballMatchListScreenPreview() {
    FootballMatchListScreen(
        modifier = Modifier.fillMaxSize(),
        mainViewModel = hiltViewModel()
    )
}
