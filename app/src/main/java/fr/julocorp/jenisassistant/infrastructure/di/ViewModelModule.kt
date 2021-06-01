package fr.julocorp.jenisassistant.infrastructure.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarViewModel
import fr.julocorp.jenisassistant.ui.calendar.schedule.RappelViewModel
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DateTimePickerViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(RappelViewModel::class)
    abstract fun reminderViewModel(viewModel: RappelViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    abstract fun calendarViewModel(viewModel: CalendarViewModel): ViewModel
}