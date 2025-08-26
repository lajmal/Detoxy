package com.ajmal.quotesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.ajmal.quotesapp.domain.repository.DefaultQuoteStylePreferences
import com.ajmal.quotesapp.presentation.screens.share_screen.QuoteStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareQuoteViewModel @Inject constructor(
    private val defaultQuoteStylePreferences: DefaultQuoteStylePreferences
): ViewModel() {

    fun getDefaultQuoteStyle(): QuoteStyle{
        return defaultQuoteStylePreferences.getDefaultQuoteStyle()
    }

    fun changeDefaultQuoteStyle(quoteStyle: QuoteStyle){
        defaultQuoteStylePreferences.saveDefaultQuoteStyle(quoteStyle)
    }
}