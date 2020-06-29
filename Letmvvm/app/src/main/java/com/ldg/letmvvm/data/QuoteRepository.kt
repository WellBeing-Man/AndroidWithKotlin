package com.ldg.letmvvm.data

class QuoteRepository private constructor(private val quoteDao: FakeQuoteDao){

    fun addQuote(quote: Quote){
        quoteDao.addQuotes(quote)
    }
    fun getQuote()=quoteDao.getQuotes()
    companion object{
        @Volatile  private var instance:QuoteRepository?=null

        fun getInstance(quoteDao: FakeQuoteDao)=
            instance?: synchronized(lock = this){
                instance?: QuoteRepository(quoteDao).also { instance=it }
            }
    }
}