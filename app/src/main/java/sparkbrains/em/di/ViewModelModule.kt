package sparkbrains.em.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import sparkbrains.em.features.auth.login.viewmodel.LoginViewModel

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}