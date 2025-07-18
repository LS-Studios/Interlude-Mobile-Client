package de.stubbe.interlude

import android.app.Application
import de.stubbe.interlude.di.initKoin
import org.koin.android.ext.koin.androidContext

class InterludeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@InterludeApplication)
        }
    }
}