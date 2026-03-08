package com.om.ecommercedemo.di


import com.om.ecommercedemo.repositories.HomeRepository
import com.om.ecommercedemo.repositories.HomeRepositoryImpl
import com.om.ecommercedemo.ui.features.detail.DetailViewModel
import com.om.ecommercedemo.ui.features.home.HomeViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {

    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
}

val dataModule = module {

    singleOf(::HomeRepositoryImpl) bind HomeRepository::class

}

fun initApplication(appDeclaration: KoinAppDeclaration = {}) : KoinApplication {
    return startKoin {
        appDeclaration()
        modules(networkModule,dataModule,appModule)
    }
}

fun initKoin() = initApplication {  }

