package com.ajmal.quotesapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class QuoteApplication:Application(), Configuration.Provider {

   @Inject
   lateinit var hiltWorkerFactory: HiltWorkerFactory



    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(hiltWorkerFactory).build()


    override fun onCreate() {
        super.onCreate()
    }


}