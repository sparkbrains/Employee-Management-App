package sparkbrains.em.network.di

import sparkbrains.em.network.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sparkbrains.em.network.client.KtorClient
import sparkbrains.em.network.connectivity.ConnectivityObserver
import sparkbrains.em.network.connectivity.NetworkConnectivityObserver
import java.util.concurrent.TimeUnit

val networkModule = module {
    singleOf(::NetworkConnectivityObserver) bind ConnectivityObserver::class
    single { provideOkHttpClient() }
    single { provideHttpClient(get<OkHttpClient>()) }
    factory { KtorClient(get<HttpClient>()) }
}

private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .followRedirects(true)
    .retryOnConnectionFailure(true)
    .apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }
    .build()

fun provideHttpClient(okHttpClient: OkHttpClient) = HttpClient(OkHttp) {
    engine {
        preconfigured = okHttpClient
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }

    install(DefaultRequest) {
        url {
            protocol = URLProtocol.HTTP
            host = BuildConfig.BASE_URL
            port = 8000
            path(BuildConfig.BASE_URL_SUFFIX)
        }
        accept(Json)
        contentType(Json)
    }

    install(Logging) {
        logger = Logger.DEFAULT
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 30_000
        connectTimeoutMillis = 30_000
        socketTimeoutMillis = 30_000
    }
}