package com.ajmal.quotesapp.presentation.screens.fav_screen.util

import com.ajmal.quotesapp.domain.model.Quote

data class FavQuoteState (
    val dataList:List<Quote> = emptyList(),
    val liked:Boolean=false,
    val error:String="",
    val isLoading: Boolean =false,
    val isRefreshing: Boolean = false,
    val query:String = ""
)
