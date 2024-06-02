package presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import domain.Article

data class ArticlesScreenState(
    val screenState: MutableState<ScreenState> = mutableStateOf(ScreenState.Loading),
    val articles: MutableState<List<Article>> = mutableStateOf(emptyList()),
    val errorMessage: MutableState<String> = mutableStateOf("error"),
    val query: MutableState<String> = mutableStateOf("")
)

sealed class ScreenState() {

    data object Error: ScreenState()

    data object Articles: ScreenState()

    data object Loading: ScreenState()

}
