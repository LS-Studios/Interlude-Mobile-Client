package de.stubbe.interlude.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import de.stubbe.interlude.database.DatabaseFactory
import de.stubbe.interlude.database.InterludeDatabase
import de.stubbe.interlude.network.HttpClientFactory
import de.stubbe.interlude.network.InterludeSongDataSource
import de.stubbe.interlude.repository.AppShareRepository
import de.stubbe.interlude.repository.CachedPlatformRepository
import de.stubbe.interlude.repository.HistoryRepository
import de.stubbe.interlude.repository.InterludeNetworkRepository
import de.stubbe.interlude.viewmodel.ConverterScreenViewModel
import de.stubbe.interlude.viewmodel.HistoryScreenViewModel
import de.stubbe.interlude.viewmodel.SettingsViewModel
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
    viewModelOf(::SettingsViewModel)
    viewModelOf(::HistoryScreenViewModel)
}

val daoModule = module {
    single { get<InterludeDatabase>().convertedLinkDao }
    single { get<InterludeDatabase>().cachedPlatformDao }
    single { get<InterludeDatabase>().historyDao }
}

val repositoryModule = module {
    singleOf(::HistoryRepository)
    singleOf(::CachedPlatformRepository)
    singleOf(::InterludeNetworkRepository)
    singleOf(::AppShareRepository)
}

val networkModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::InterludeSongDataSource)
}