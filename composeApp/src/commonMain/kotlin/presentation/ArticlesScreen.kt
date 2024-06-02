package presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import presentation.components.ArticleCard

class ArticlesScreen : Screen {
    @Composable
    override fun Content() {
        val model = getScreenModel<ArticlesScreenModel>()
        model.refreshArticles()
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (model.state.screenState.value) {
                is ScreenState.Loading -> {
                    CircularProgressIndicator()
                }

                is ScreenState.Error -> {
                    Text((model.state.screenState.value as ScreenState.Error).message)
                }

                is ScreenState.Articles -> {
                    LazyColumn {
                        items((model.state.screenState.value as ScreenState.Articles).list) {
                            ArticleCard(article = it,
                                 modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}
