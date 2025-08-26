package com.ajmal.quotesapp.domain.usecases.home_screen_usecases


import com.ajmal.quotesapp.domain.model.Quote
import com.ajmal.quotesapp.domain.repository.QuoteRepository
import javax.inject.Inject

class LikedQuote @Inject constructor(val quoteRepository: QuoteRepository) {

    suspend operator fun  invoke(quote:Quote):Quote{

        val updatedQuote = quote.copy(liked = !quote.liked)
        quoteRepository.saveLikedQuote(updatedQuote)

        return updatedQuote
    }
}