package com.syntex_error.jepackcompose.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.syntex_error.jepackcompose.ui.theme.JepackComposeTheme
import com.syntex_error.jepackcompose.viewmodels.DogListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    @ExperimentalCoroutinesApi
    private val dogListViewModel: DogListViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            ComposeView(this)
                .apply {
                    setContent {
                        DoglistScreen(dogListViewModel ,lifecycleScope)
                    }
                }
        )

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JepackComposeTheme {

    }
}