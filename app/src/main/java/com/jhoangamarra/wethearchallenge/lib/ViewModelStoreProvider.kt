package com.jhoangamarra.wethearchallenge.lib

import androidx.compose.runtime.staticCompositionLocalOf

val LocalViewModelStore = staticCompositionLocalOf<ViewModelStore> {
    error("ViewModel must be provided first")
}