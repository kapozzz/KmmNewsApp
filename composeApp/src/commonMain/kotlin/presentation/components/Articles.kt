package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import presentation.ArticlesScreenModel

@Composable
fun Articles(
    model: ArticlesScreenModel,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState
        ) {
            item {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = model.state.query.value,
                    onValueChange = {
                        model.state.query.value = it
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                model.refreshArticles()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
            items(model.state.articles.value) { article ->
                ArticleCard(
                    article = article,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        if (listState.firstVisibleItemIndex >= 5) IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(60.dp)
                .clip(CircleShape),
            onClick = {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            }
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary),
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}