package com.kt.apps.media.football.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kt.apps.media.football.R
import com.kt.apps.media.football.ui.screens.FootballLiveNavScreens
import com.kt.apps.media.football.ui.theme.buttonTextColor
import com.kt.apps.media.football.ui.theme.textColor


@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel,
    navController: NavHostController
) {
    val uiState = splashViewModel.uiState.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_splash),
            contentDescription = "Splash Screen",
            modifier = Modifier.clip(
                shape = RoundedCornerShape(8.dp)
            )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TextWithShadow(text = "Live Matches")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Watch exciting football games live",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(64.dp))
            if (uiState.value.isFirstTimeOpen) {
                ElevatedButton(
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.White
                    ),
                    onClick = {
                        navController.navigate(FootballLiveNavScreens.MAIN_SCREEN.route) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    }, modifier = Modifier
                        .defaultMinSize(minHeight = 48.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(50)
                        )
                        .shadow(
                            elevation = 6.dp,
                            ambientColor = Color.Black.copy(alpha = 0.5f),
                            spotColor = Color.Black.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    Text(
                        text = "Join now",
                        style = TextStyle.Default
                            .copy(
                                color = buttonTextColor,
                                fontWeight = FontWeight.Bold,
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun TextWithShadow(
    text: String = "Live Match"
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Spacer(
            modifier = Modifier
                .height(32.dp)
                .width(260.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RectangleShape,
                    ambientColor = Color.Black.copy(alpha = 0.2f),
                    spotColor = Color.Black.copy(alpha = 0.15f),
                )
        )
        Text(
            text = text,
            color = textColor,
            fontSize = 24.sp,
            style = TextStyle.Default.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable()
@Preview(
    showBackground = true,
    name = "Splash Screen",
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
fun SplashScreenPreview() {
    SplashScreen(
        hiltViewModel(),
        rememberNavController()
    )
}