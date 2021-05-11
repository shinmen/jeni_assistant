package fr.julocorp.jenisassistant.infrastructure

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import fr.julocorp.jenisassistant.infrastructure.di.DaggerAppComponent
import javax.inject.Inject


class JenisAssistantApplication : Application(), HasAndroidInjector {

    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
//        DaggerAppComponent.create()
//            .injectApplication(this)

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}