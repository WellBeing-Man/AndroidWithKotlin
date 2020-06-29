package com.ldg.letmvvm.utility

import com.ldg.letmvvm.data.FakeDataBase
import com.ldg.letmvvm.data.QuoteRepository
import com.ldg.letmvvm.ui.quotes.QuotesViewModelFactory

object ObjectInjector {
    fun provideQuoteViewModelFacotory():QuotesViewModelFactory{
        val quoteRepository=QuoteRepository.getInstance(FakeDataBase.getInstance().quoteDao)
        return QuotesViewModelFactory(quoteRepository)
    }
}