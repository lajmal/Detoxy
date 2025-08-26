package com.ajmal.quotesapp.domain.usecases.fav_screen_usecases

import com.ajmal.quotesapp.domain.model.Quote
import com.ajmal.quotesapp.domain.repository.FavQuoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavQuote @Inject constructor(private val quoteRepository: FavQuoteRepository) {

    operator fun invoke(query: String): Flow<List<Quote>> {
        return quoteRepository.getAllLikedQuotes(query)
    }

}