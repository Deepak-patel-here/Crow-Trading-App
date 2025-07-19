package com.deepakjetpackcompose.crowtradingapp.domain.di

import com.deepakjetpackcompose.crowtradingapp.data.remote.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json as KotlinxJson
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(json = KotlinxJson {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            level = LogLevel.BODY
        }
    }

    @Provides
    @Singleton
    fun provideCoinRepository(apiClient: HttpClient): CoinRepository {
        return CoinRepository(apiClient)
    }
}
