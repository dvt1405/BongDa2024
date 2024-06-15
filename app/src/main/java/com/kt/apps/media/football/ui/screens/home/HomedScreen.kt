package com.kt.apps.media.football.ui.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.kt.apps.media.football.ui.screens.dashboard_pager.DashboardHomePager
import com.kt.apps.media.football.ui.screens.dashboard_pager.XemBongDaPage
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HomedScreen(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = hiltViewModel(LocalContext.current as androidx.activity.ComponentActivity)
) {
    val homeUIState = mainViewModel.homeUIState.collectAsState()
    val scrollState = rememberScrollState()
    val bottomBarVisibleState = remember {
        mutableStateOf(true)
    }
    val topBarVisibleState = remember {
        mutableStateOf(true)
    }
    val bottomBarSelectedState = remember {
        mutableStateOf(0)
    }
    val pages = XemBongDaPage.entries.toTypedArray()
    val pagerState = rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(scrollState.value) {
        bottomBarVisibleState.value = scrollState.value <= 0
    }
    Scaffold(
        topBar = {
            AnimatedVisibility(visible = topBarVisibleState.value) {
                TopAppBar(title = {
                    Text(
                        "Welcome to\nFootball Live App!",
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
                        contentDescription = "Notification"
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
        },
        bottomBar = {
            HomeNavigationBar(bottomBarVisibleState = bottomBarVisibleState,
                bottomBarSelectedState = bottomBarSelectedState,
                onItemClick = { index ->
                    bottomBarSelectedState.value = index
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                })
        },
        content = {
            Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                DashboardHomePager(
                    pages = pages,
                    pagerState = pagerState,
                    navHostController = navController
                )
            }
        }
    )
}

@Composable()
@Preview(showBackground = true)
fun DashScreenPreview() {
    HomedScreen()
}

