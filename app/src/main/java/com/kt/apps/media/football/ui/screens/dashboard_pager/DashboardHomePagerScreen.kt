@file:OptIn(ExperimentalFoundationApi::class)

package com.kt.apps.media.football.ui.screens.dashboard_pager

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kt.apps.media.football.R
import com.kt.apps.media.football.ui.screens.football_match_list.FootballMatchListScreen

enum class XemBongDaPage(
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    HOME(R.string.home_title, R.drawable.ic_info),
    LIVE(R.string.live_title, R.drawable.ic_info),
    HIGHLIGHT(R.string.highlight_title, R.drawable.ic_info),
    SETTINGS(R.string.settings_title, R.drawable.ic_info)
}

@Composable
fun DashboardHomePager(
    pages: Array<XemBongDaPage> = XemBongDaPage.entries.toTypedArray(),
    pagerState: PagerState = rememberPagerState { pages.size },
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    HorizontalPager(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        state = pagerState,
        verticalAlignment = Alignment.Top,
        userScrollEnabled = false,
    ) { index ->
        when (pages[index]) {
            XemBongDaPage.HOME -> {
                FootballMatchListScreen(
                    navHostController = navHostController,
                )
            }

            XemBongDaPage.SETTINGS -> {
                FootballMatchListScreen(
                    navHostController = navHostController,
                    modifier = modifier
                )
            }

            else -> {
                FootballMatchListScreen(
                    navHostController = navHostController
                )
            }
        }
    }
}