package presentation

import org.koin.dsl.module

val articlesModule = module {
    factory { ArticlesScreenModel(get()) }
}