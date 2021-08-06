package com.syntex_error.jepackcompose.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import coil.compose.rememberImagePainter
import com.syntex_error.jepackcompose.commonViews.CircularIndeterminateProgressBar
import com.syntex_error.jepackcompose.commonViews.DefaultSnackbar
import com.syntex_error.jepackcompose.commonViews.utils.SnackbarController
import com.syntex_error.jepackcompose.models.DogModel
import com.syntex_error.jepackcompose.viewmodels.DogListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun DoglistScreen(dogListViewModel: DogListViewModel, lifecycleScope: LifecycleCoroutineScope) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Dogs") })
            },
            content = {
                DogFeed(dogListViewModel, lifecycleScope)
            }
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun DogFeed(dogListViewModel: DogListViewModel, lifecycleScope: LifecycleCoroutineScope) {
    val recipes = dogListViewModel.dogList.value
    val isEnd = dogListViewModel.isEnd.value
    val lastIndex = recipes.lastIndex
    val isLoading = dogListViewModel.isLoading.value
    val scaffoldState = rememberScaffoldState()
    val snackbarController = SnackbarController(lifecycleScope)

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
            .fillMaxWidth()

    ) {
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 50.dp)
        )
        {
            itemsIndexed(
                items = recipes,
                itemContent = { index, item ->
                    PuppyListItem(item)
                    if (index == lastIndex && !isLoading && !isEnd) {
                        dogListViewModel.getMoreDogs()
                    } else if (isEnd && index == lastIndex) {
                        showSnackBar(snackbarController, scaffoldState, dogListViewModel)
                    }
                })

        }

        Row(
            modifier = Modifier
                .align(alignment = BottomCenter)
                .padding(30.dp)

        ) {
            CircularIndeterminateProgressBar(
                isDisplayed = isLoading
            )
        }

        DefaultSnackbar(
            snackBarHostState = scaffoldState.snackbarHostState,
            onDismiss = {

                Log.d(
                    "TAG",
                    "LatestNewsFeed:  ${scaffoldState.snackbarHostState.currentSnackbarData?.actionLabel}"
                )
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()

            },
            modifier = Modifier.align(BottomCenter)
        )


        // will show a snackbar if any error msg
        if (dogListViewModel.isEnd.value || dogListViewModel.isError.value) {

            showSnackBar(snackbarController, scaffoldState, dogListViewModel)

        }

    }


}

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
fun showSnackBar(
    snackbarController: SnackbarController,
    scaffoldState: ScaffoldState,
    dogListViewModel: DogListViewModel
) {
    snackbarController.getScope().launch {
        snackbarController.showSnackbar(
            scaffoldState = scaffoldState,
            message = dogListViewModel.isMsg.value,
            actionLabel = "Dismiss"
        )

    }
}

@ExperimentalCoroutinesApi
@Composable
fun PuppyListItem(model: DogModel) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(85.dp),
        elevation = 2.dp,
        backgroundColor = White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            PuppyImage(model.image?.url)
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.Top)
            ) {
                //  Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "${model.name}",
                    color = Black,
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(2.dp))
                var bredgroup = model.breed_group
                if (bredgroup.isNullOrBlank()) bredgroup = "N/A"
                Text(text = "$bredgroup", color = Black, style = MaterialTheme.typography.subtitle1)
            }
        }
    }

}

@Composable
fun PuppyImage(link: String?) {
    Image(
        painter = rememberImagePainter(
            data = link,
            builder = {
                crossfade(true)
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(85.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))

    )
}