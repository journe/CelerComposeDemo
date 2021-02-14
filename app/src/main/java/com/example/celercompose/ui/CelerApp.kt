package com.example.celercompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientAnimationClock
import androidx.compose.ui.unit.dp
import com.example.celercompose.data.room.PictureItem
import com.example.celercompose.ui.item.ItemPicture
import com.example.celercompose.ui.item.ItemVideo
import com.example.celercompose.util.Pager
import com.example.celercompose.util.PagerState

@Composable
fun CelerApp(viewModel: CelerViewModel) {

    val picturesState = viewModel.pictures.observeAsState()

    Column(Modifier.fillMaxSize()) {
        val clock = AmbientAnimationClock.current
        val pagerState = remember(clock) { PagerState(clock) }
        picturesState.value?.let {
            ItemVideoTabs(
                categories = it,
                pagerState = pagerState,
                modifier = Modifier
                    .wrapContentHeight()
                    .heightIn(max = 400.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.preferredHeight(8.dp))
            ItemPictureTabs(
                categories = it,
                pagerState = pagerState,
                modifier = Modifier
                    .padding(start = 24.dp, top = 16.dp, end = 24.dp)
                    .fillMaxWidth()
                    .heightIn(128.dp)
            )
        }
    }
}

@Composable
fun ItemVideoTabs(
    modifier: Modifier = Modifier,
    categories: List<PictureItem>,
    pagerState: PagerState = run {
        val clock = AmbientAnimationClock.current
        remember(clock) { PagerState(clock) }
    }
) {
    pagerState.maxPage = (categories.size - 1).coerceAtLeast(0)
    Pager(
        state = pagerState,
        modifier = modifier
    ) {
        ItemVideo(
            videoUrl = categories[page].videoUrl,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Composable
fun ItemPictureTabs(
    modifier: Modifier = Modifier,
    categories: List<PictureItem>,
    pagerState: PagerState = run {
        val clock = AmbientAnimationClock.current
        remember(clock) { PagerState(clock) }
    },
) {
    pagerState.maxPage = (categories.size - 1).coerceAtLeast(0)
    Pager(
        state = pagerState,
        modifier = modifier
    ) {
        ItemPicture(
            url = categories[page].imageUrl,
            modifier = Modifier
                .size(200.dp)
        )
    }
}
