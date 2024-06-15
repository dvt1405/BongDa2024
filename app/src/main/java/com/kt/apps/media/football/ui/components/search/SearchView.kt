package com.kt.apps.media.football.ui.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kt.apps.media.football.ui.components.shadow
import com.kt.apps.media.football.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
) {
    val text = remember {
        mutableStateOf("")
    }
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    BasicTextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        modifier = modifier
            .height(44.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(50))
            .shadow(
                elevation = 10.dp,
                spotColor = Color.Black.copy(alpha = 0.5f),
                ambientColor = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(50)
            )
            .background(Color.White)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,
        enabled = true,
        singleLine = true,
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = text.value,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(
                horizontal = 12.dp
            ),
            placeholder = {
                Text(
                    text = "Search",
                    style = TextStyle(
                        fontSize = 11.sp,
                        color = textColor.copy(
                            alpha = 0.6f
                        )
                    ),
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
        )
    }
}

@Composable
@Preview
fun SearchViewPreview() {
    Box(modifier = Modifier.padding(top = 40.dp, start = 16.dp, end = 16.dp)) {
        SearchView()
    }
}