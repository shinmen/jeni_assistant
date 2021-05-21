package fr.julocorp.jenisassistant.infrastructure.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import fr.julocorp.jenisassistant.infrastructure.JenisAssistantApplication
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class,
        RemoteModule::class,
        LocalDbModule::class,
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: JenisAssistantApplication)
}