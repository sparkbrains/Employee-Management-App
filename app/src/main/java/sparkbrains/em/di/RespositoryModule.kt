package sparkbrains.em.di

import org.koin.dsl.module
import sparkbrains.em.features.auth.login.repository.LoginRepository

val repositoryModule = module {
    single { LoginRepository(get()) }
}