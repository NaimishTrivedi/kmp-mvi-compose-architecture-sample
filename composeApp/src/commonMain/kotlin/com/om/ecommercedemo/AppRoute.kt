package com.om.ecommercedemo

sealed class AppRoute (val route: String) {
    object Home : AppRoute("home")
    object ProductDetail : AppRoute("product_detail")
}