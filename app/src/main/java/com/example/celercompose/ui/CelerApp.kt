package com.example.celercompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.emptyContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.example.celercompose.data.PictureItem

@Composable
fun CelerApp() {
    val viewModel: CelerViewModel = viewModel()
    val picturesState = viewModel.pictures.observeAsState()
    val selectedItem = viewModel.selected.observeAsState()

    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.preferredHeight(8.dp))
        picturesState.value?.let {
            ItemVideoTabs(
                categories = it,
                selectedItem = selectedItem.value!!,
                selected = viewModel::onSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(512.dp)
            )
            Spacer(Modifier.preferredHeight(8.dp))
            ItemPictureTabs(
                categories = it,
                selectedItem = selectedItem.value!!,
                selected = viewModel::onSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
            )
        }
    }
}

private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}

@Composable
fun ItemVideoTabs(
    categories: List<PictureItem>,
    selectedItem: PictureItem,
    selected: (PictureItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = categories.indexOfFirst { it == selectedItem }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        divider = emptyContent(), /* Disable the built-in divider */
        edgePadding = 24.dp,
        backgroundColor = MaterialTheme.colors.background,
        indicator = emptyTabIndicator,
        modifier = modifier
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { selected(category) }
            ) {
                ItemVideo(
                    videoUrl = selectedItem.videoUrl ,
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(320.dp)
                )
            }
        }
    }
}
@Composable
fun ItemPictureTabs(
    categories: List<PictureItem>,
    selectedItem: PictureItem,
    selected: (PictureItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = categories.indexOfFirst { it == selectedItem }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        divider = emptyContent(), /* Disable the built-in divider */
        edgePadding = 24.dp,
        backgroundColor = MaterialTheme.colors.background,
        indicator = emptyTabIndicator,
        modifier = modifier
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { selected(category) }
            ) {
                ItemPicture(
                    url = category.imageUrl,
                    modifier = Modifier
                        .padding(horizontal = 64.dp, vertical = 16.dp)
                )
            }
        }
    }
}
