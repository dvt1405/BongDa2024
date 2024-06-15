package com.kt.apps.media.football.ui.screens.dialogs

import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
private fun CoreDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes confirmButton: Int,
    @StringRes dismissButton: Int,
    onConfirmButton: () -> Unit,
    onDismissButton: () -> Unit,
    modifier: Modifier = Modifier
) {
//    val activity = (LocalContext.current as Activity)
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
        },
        title = { Text(stringResource(title)) },
        text = { Text(stringResource(message, 0)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissButton()
                }
            ) {
                Text(text = stringResource(dismissButton))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmButton()
                }
            ) {
                Text(text = stringResource(confirmButton))
            }
        }
    )
}