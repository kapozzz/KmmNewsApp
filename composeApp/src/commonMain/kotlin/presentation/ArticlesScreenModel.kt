package presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
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

    // TODO надо ли проверять что isActive?
    fun refreshArticles() {
        job?.cancel()
        job = screenModelScope.launch(Dispatchers.IO) {
            if (coroutineContext[Job]?.isActive == true) {
                newsRepository.getAll().collect {
                    when (it) {
                        is Resource.Failure -> {
                            state.screenState.value = ScreenState.Error(it.message)
                        }

                        is Resource.Loading -> {
                            state.screenState.value = ScreenState.Loading
                        }

                        is Resource.Success -> {
                            state.screenState.value = ScreenState.Articles(it.result)
                        }
                    }
                }
            }
        }
    }
}