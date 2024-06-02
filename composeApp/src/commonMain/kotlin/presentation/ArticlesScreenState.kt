package presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import domain.Article

data class ArticlesScreenState(
    val screenState: MutableState<ScreenState> = mutableStateOf(ScreenState.Loading)
)

sealed class ScreenState() {

    data class Error(val message: String): ScreenState()

    data class Articles(val list: List<Article>): ScreenState()

    data object Loading: ScreenState()

}
