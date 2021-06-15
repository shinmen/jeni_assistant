package fr.julocorp.jenisassistant.infrastructure.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fr.julocorp.jenisassistant.ui.MainActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity
}