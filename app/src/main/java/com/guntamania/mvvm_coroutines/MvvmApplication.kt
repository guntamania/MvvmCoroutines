package com.guntamania.mvvm_coroutines

import android.app.Application
import com.guntamania.mvvm_coroutines.datasource.ApiClient
import com.guntamania.mvvm_coroutines.datasource.Repository
import com.guntamania.mvvm_coroutines.datasource.RepositoryImpl
import com.guntamania.mvvm_coroutines.ui.main.MainViewModel
import com.guntamania.mvvm_coroutines.ui.sub01.Sub01ViewModel
import com.guntamania.mvvm_coroutines.ui.sub02.Sub02ViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MvvmApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger(org.koin.core.logger.Level.ERROR)

            // use the Android context given there
            androidContext(this@MvvmApplication)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(appModule)
        }
    }

    private val appModule = module {
        // ViewModel instance of MyViewModel
        // get() will resolve Repository instance
        viewModel { MainViewModel(get()) }
        viewModel { Sub01ViewModel(get()) }
        viewModel { Sub02ViewModel(get()) }

        // Single instance of Repository
        factory { RepositoryImpl(get()) as Repository }

        factory { ApiClient() }
    }
}