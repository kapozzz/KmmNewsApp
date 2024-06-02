package presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.Article
import domain.NewsRepository
import domain.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArticlesScreenModel(
    private val newsRepository: NewsRepository
) : ScreenModel {

    private var job: Job? = null

    val state = ArticlesScreenState()

    fun refreshArticles() {
        job?.cancel()
        job = screenModelScope.launch(Dispatchers.Main) {
            newsRepository.getAll(if (state.query.value.isEmpty()) "cats" else state.query.value)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            loading()
                        }

                        is Resource.Failure -> {
                            failure(it.message)
                        }

                        is Resource.Success -> {
                            success(it.result)
                        }
                    }
                }
        }
    }

    private fun loading() {
        state.screenState.value = ScreenState.Loading
    }

    private fun failure(message: String) {
        state.screenState.value = ScreenState.Error
        state.errorMessage.value = message
    }

    private fun success(list: List<Article>) {
        state.screenState.value = ScreenState.Articles
        state.articles.value = list
    }


}