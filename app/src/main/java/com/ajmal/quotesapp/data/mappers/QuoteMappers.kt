package com.ajmal.quotesapp.data.mappers

import com.ajmal.quotesapp.data.remote.dto.QuotesDtoItem
import com.ajmal.quotesapp.domain.model.Quote


fun QuotesDtoItem.toQuote():Quote{
    return  Quote(quote =q, author =a, liked = false)
}