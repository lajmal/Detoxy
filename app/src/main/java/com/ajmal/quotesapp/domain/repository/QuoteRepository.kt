package com.ajmal.quotesapp.domain.repository

import com.ajmal.quotesapp.domain.model.Quote
import com.ajmal.quotesapp.domain.model.QuoteHome
import com.ajmal.quotesapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun getQuote(): Flow<Resource<QuoteHome>>

     suspend  fun saveLikedQuote(quote: Quote)

}