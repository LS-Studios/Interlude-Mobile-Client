package de.stubbe.interlude.platform.di 

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import de.stubbe.interlude.data.database.DatabaseFactory
import de.stubbe.interlude.data.database.InterludeDatabase
import de.stubbe.interlude.data.network.HttpClientFactory
import de.stubbe.interlude.data.network.InterludeSongDataSource
import de.stubbe.interlude.domain.repository.AppShareRepository
import de.stubbe.interlude.domain.repository.CachedPlatformRepository
import de.stubbe.interlude.domain.repository.HistoryRepository
import de.stubbe.interlude.domain.repository.InterludeNetworkRepository
import de.stubbe.interlude.presentation.viewmodel.ConverterScreenViewModel
import de.stubbe.interlude.presentation.viewmodel.HistoryScreenViewModel
import de.stubbe.interlude.presentation.viewmodel.SettingsViewModel
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