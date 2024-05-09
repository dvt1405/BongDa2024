package com.kt.apps.media.football.di.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FootballRepository(
    val value: FootballRepositoryType
) {}

enum class FootballRepositoryType {
    XoiLac,
    VeBo,
    XoiLacZCO
}

