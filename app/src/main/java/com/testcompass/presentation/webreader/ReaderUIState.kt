package com.testcompass.presentation.webreader

data class ReaderUIState(
    val url1 : String = "",
    val website10s : ArrayList<String> = arrayListOf(),
    val url2 : String = "",
    val uniques : Int = 0,
    val isLoading : Boolean  = false,
)