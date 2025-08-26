package com.ajmal.quotesapp.domain.repository

import com.ajmal.quotesapp.presentation.screens.share_screen.QuoteStyle

interface DefaultQuoteStylePreferences {
    fun saveDefaultQuoteStyle(quoteStyle: QuoteStyle)
    fun getDefaultQuoteStyle(): QuoteStyle
}