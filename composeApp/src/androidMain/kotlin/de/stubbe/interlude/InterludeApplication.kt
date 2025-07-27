package de.stubbe.interlude

import android.app.Application
import de.stubbe.interlude.database.InterludeDatabase
import de.stubbe.interlude.di.initKoin
import multiplatform.network.cmptoast.AppContext
import org.koin.android.ext.koin.androidContext

class InterludeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //this.deleteDatabase(InterludeDatabase.DB_NAME)

        AppContext.apply { set(applicationContext) }

        initKoin {
            androidContext(this@InterludeApplication)
        }
    }
}