package com.syntex_error.jepackcompose.commonViews


import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.syntex_error.jepackcompose.ui.theme.JepackComposeTheme

/**
 * Center a circular indeterminate progress bar with optional vertical bias.
 *
 * NOTE: You do not need a ConstraintLayout here. A Row would have been perfectly fine.
 * I just left it here as an example.
 */


@Composable
fun CircularIndeterminateProgressBar(isDisplayed: Boolean) {
    if (isDisplayed) {

        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )


    }

}




@Preview(showBackground = true)
@Composable
fun DefaultPreviewd() {
    JepackComposeTheme {
        CircularIndeterminateProgressBar(true)
    }
}
