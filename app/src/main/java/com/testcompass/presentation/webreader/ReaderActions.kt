package com.testcompass.presentation.webreader

sealed interface ReaderActions {

    data class ReadWebsite(var url: String = "") : ReaderActions
}