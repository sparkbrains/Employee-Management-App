package sparkbrains.em

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import sparkbrains.em.di.repositoryModule
import sparkbrains.em.di.viewModelModule
import sparkbrains.em.network.di.networkModule

class EmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@EmApplication)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}