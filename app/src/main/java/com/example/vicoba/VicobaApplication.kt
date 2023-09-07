package com.example.vicoba

import android.app.Application
import com.example.vicoba.data.di.AppContainer
import com.example.vicoba.data.di.DefaultAppContainer

/**
 * VicobaApplication is a subclass of the android Application class that holds references
 * to the vicoba app container with a list of dependencies required by the app
 */
class VicobaApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        /**Refference to the app container*/
        container = DefaultAppContainer()
    }
}