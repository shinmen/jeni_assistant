package fr.julocorp.jenisassistant.infrastructure.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarFragment
import fr.julocorp.jenisassistant.ui.calendar.schedule.CalendarEstimationFragment
import fr.julocorp.jenisassistant.ui.calendar.schedule.RappelFragment
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DatePickerDialogFragment
import fr.julocorp.jenisassistant.ui.common.datetimePicker.TimePickerDialogFragment
import fr.julocorp.jenisassistant.ui.mandatVente.estimation.EstimationFragment
import fr.julocorp.jenisassistant.ui.mandatVente.propriete.ProprieteFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeCalendarFragment(): CalendarFragment

    @ContributesAndroidInjector
    abstract fun contributeReminderFragment(): RappelFragment

    @ContributesAndroidInjector
    abstract fun contributeDatePickerFragment(): DatePickerDialogFragment

    @ContributesAndroidInjector
    abstract fun contributeTimePickerFragment(): TimePickerDialogFragment

    @ContributesAndroidInjector
    abstract fun contributeCalendarEstimationFragment(): CalendarEstimationFragment

    @ContributesAndroidInjector
    abstract fun contributeEstimationFragment(): EstimationFragment

    @ContributesAndroidInjector
    abstract fun contributeProprieteFragment(): ProprieteFragment
}