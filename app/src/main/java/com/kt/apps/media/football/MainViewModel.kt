package com.kt.apps.media.football

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kt.apps.media.api.IFootballMatchRepository
import com.kt.apps.media.football.di.annotations.FootballRepository
import com.kt.apps.media.football.di.annotations.FootballRepositoryType
import com.kt.apps.media.football.ui.components.home.backgroundBlur
import com.kt.apps.media.football.ui.screens.home.models.HomeUIState
import com.kt.apps.media.football.ui.screens.home.models.toHomeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    @FootballRepository(FootballRepositoryType.XoiLacZCO)
    private val _xoilacZCORepository: IFootballMatchRepository
) : ViewModel() {
    private val _homeUIState = MutableStateFlow(HomeUIState())

    val homeUIState: StateFlow<HomeUIState> = _homeUIState.asStateFlow()

    init {
        getMatches()
    }

    fun getMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeUIState.emit(_homeUIState.value.copy(isLoading = true))
            _xoilacZCORepository.getAllMatches()
                .catch {
                    delay(2000)
                    getMatches()
                }
                .collectLatest {
                    _homeUIState.emit(
                        _homeUIState.value.copy(
                            upcomingMatches = it,
                            liveMatches = it.filter {
                                it.score.isNotEmpty() && !it.liveStatus.equals(
                                    "FT",
                                    ignoreCase = true
                                ) && !it.liveStatus.equals(
                                    "KT",
                                    ignoreCase = true
                                )
                            }.map {
                                it.toHomeItem(backgroundBlur[Random.nextInt(0, backgroundBlur.size)])
                            },
                            isLoading = false
                        )
                    )
                }
        }
    }
}