package com.podium.technicalchallenge.ui.components

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.podium.technicalchallenge.R

@Composable
fun ErrorSnackBar(
    scaffoldState: ScaffoldState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    // TODO change message based on error
    val message = stringResource(id = R.string.error_loading)
    val actionLabel = stringResource(id = R.string.try_again)

    LaunchedEffect(message) {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Long
        )

        when (result) {
            SnackbarResult.Dismissed -> onDismiss()
            SnackbarResult.ActionPerformed -> onConfirm()
        }
    }
}