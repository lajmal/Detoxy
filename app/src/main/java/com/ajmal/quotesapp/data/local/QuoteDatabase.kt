package com.ajmal.quotesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajmal.quotesapp.domain.model.Quote


@Database(entities = [Quote::class], version = 3)
abstract class QuoteDatabase:RoomDatabase() {

    abstract fun getQuoteDao():QuoteDao

}