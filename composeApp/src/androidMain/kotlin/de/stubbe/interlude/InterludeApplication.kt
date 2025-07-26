package de.stubbe.interlude

import android.app.Application
import de.stubbe.interlude.database.InterludeDatabase
import de.stubbe.interlude.di.initKoin
import org.koin.android.ext.koin.androidContext

class InterludeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //this.deleteDatabase(InterludeDatabase.DB_NAME)

        initKoin {
            androidContext(this@InterludeApplication)
        }
    }
}