package com.om.ecommercedemo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.om.ecommercedemo.di.initApplication
import com.om.ecommercedemo.di.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        initApplication {

        }
        App()
    }
}