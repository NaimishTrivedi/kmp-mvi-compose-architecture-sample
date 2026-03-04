package com.om.ecommercedemo

import android.app.Application
import com.om.ecommercedemo.di.initApplication
import org.koin.android.ext.koin.androidContext

class EcommerceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initApplication {
            androidContext(this@EcommerceApp)
        }
    }
}