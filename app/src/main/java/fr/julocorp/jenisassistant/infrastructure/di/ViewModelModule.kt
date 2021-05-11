package fr.julocorp.jenisassistant.infrastructure.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import fr.julocorp.jenisassistant.infrastructure.common.DateTimePickerViewModel
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(DateTimePickerViewModel::class)
    abstract fun dateTimePickerViewModel(viewModel: DateTimePickerViewModel): ViewModel
}