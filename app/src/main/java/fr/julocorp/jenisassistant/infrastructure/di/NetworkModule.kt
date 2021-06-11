package fr.julocorp.jenisassistant.infrastructure.di

import dagger.Module
import dagger.Provides
import fr.julocorp.jenisassistant.infrastructure.common.network.ApiGeoGouvService
import fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv.ApiGeoGouvAdresseSearcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(ApiGeoGouvAdresseSearcher.API_GEO_GOUV_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ApiGeoGouvService =
        retrofit.create(ApiGeoGouvService::class.java)


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
            .apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

        return OkHttpClient.Builder()
            .followRedirects(false)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}