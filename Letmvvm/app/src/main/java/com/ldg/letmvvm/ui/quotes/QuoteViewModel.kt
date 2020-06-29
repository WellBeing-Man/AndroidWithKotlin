package com.ldg.letmvvm.ui.quotes

import androidx.lifecycle.ViewModel
import com.ldg.letmvvm.data.Quote
import com.ldg.letmvvm.data.QuoteRepository

class QuoteViewModel(private  val quoteRepository: QuoteRepository):ViewModel() {

    fun getQuotes()= quoteRepository.getQuote()

    fun addQuotes(quote: Quote)=quoteRepository.addQuote(quote)
}