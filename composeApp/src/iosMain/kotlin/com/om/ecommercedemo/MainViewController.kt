package com.om.ecommercedemo

import androidx.compose.ui.window.ComposeUIViewController
import com.om.ecommercedemo.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}