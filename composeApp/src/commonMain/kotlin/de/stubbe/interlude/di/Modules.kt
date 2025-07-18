package de.stubbe.interlude.di

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import de.stubbe.interlude.database.DatabaseFactory
import de.stubbe.interlude.viewmodel.ConverterScreenViewModel
import org.koin.core.module.Module
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
}

val daoModule = module {
   // single { get<InterludeDatabase>().messageDao }
}

val repositoryModule = module {
    //singleOf(::MessageRepository)
}