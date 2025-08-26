package com.ajmal.quotesapp.presentation.screens.home_screen.util

import com.ajmal.quotesapp.domain.model.Quote

//enum class QuoteEvent {
//    LIKE,
//    SHARE
//}
// replacing this with sealed class for more customization

sealed class QuoteEvent {
    data class Like(val quote: Quote):QuoteEvent()
    data object Retry: QuoteEvent()
}