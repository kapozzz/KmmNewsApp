package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import presentation.components.Articles

class ArticlesScreen : Screen {
    @Composable
    override fun Content() {

        val model = getScreenModel<ArticlesScreenModel>()

        LaunchedEffect(Unit) {
            model.refreshArticles()
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            topBar = {
                TopAppBar(
                    modifier = Modifier
                        .background(Color.LightGray),
                    title = {
                        Text("News")
                    },
                    actions = {
                        IconButton(
                            onClick = { model.refreshArticles() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                when (model.state.screenState.value) {
                    is ScreenState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is ScreenState.Error -> {
                        Text(model.state.errorMessage.value)
                    }
                    is ScreenState.Articles -> {
                        Articles(model)
                    }
                }
            }
        }
    }
}
