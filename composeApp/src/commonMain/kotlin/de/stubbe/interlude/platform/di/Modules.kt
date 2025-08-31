package de.stubbe.interlude.platform.di 

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import de.stubbe.interlude.data.database.DatabaseFactory
import de.stubbe.interlude.data.database.InterludeDatabase
import de.stubbe.interlude.data.network.HttpClientFactory
import de.stubbe.interlude.data.network.InterludeSongDataSource
import de.stubbe.interlude.domain.repository.CachedProviderRepository
import de.stubbe.interlude.domain.repository.HistoryRepository
import de.stubbe.interlude.domain.repository.InterludeNetworkRepository
import de.stubbe.interlude.presentation.viewmodel.ConverterScreenViewModel
import de.stubbe.interlude.presentation.viewmodel.HistoryScreenViewModel
import de.stubbe.interlude.presentation.viewmodel.SettingsViewModel
import de.stubbe.interlude.presentation.viewmodel.ShareDialogViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val databaseModule = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}

val viewModelModule = module {
    viewModelOf(::ConverterScreenViewModel)
    viewModelOf(::ShareDialogViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::HistoryScreenViewModel)
}

val daoModule = module {
    single { get<InterludeDatabase>().convertedLinkDao }
    single { get<InterludeDatabase>().cachedProviderDao }
    single { get<InterludeDatabase>().historyDao }
}

val repositoryModule = module {
    singleOf(::HistoryRepository)
    singleOf(::CachedProviderRepository)
    singleOf(::InterludeNetworkRepository)
}

val networkModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::InterludeSongDataSource)
}