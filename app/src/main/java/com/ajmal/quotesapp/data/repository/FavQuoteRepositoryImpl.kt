package com.ajmal.quotesapp.data.repository

import com.ajmal.quotesapp.data.local.QuoteDatabase
import com.ajmal.quotesapp.domain.model.Quote
import com.ajmal.quotesapp.domain.repository.FavQuoteRepository
import kotlinx.coroutines.flow.Flow

class FavQuoteRepositoryImpl (private val db: QuoteDatabase):FavQuoteRepository {

    override fun getAllLikedQuotes(query: String): Flow<List<Quote>> {

        return if (query.isNotBlank()){
            return db.getQuoteDao().searchForQuotes(query)

        }else{
          return  db.getQuoteDao().getAllLikedQuotes()
        }
    }

    override suspend fun saveLikedQuote(quote: Quote) {
        // this fun only saves liked quotes that's its job
        db.getQuoteDao().insertLikedQuote(quote)
    }
}