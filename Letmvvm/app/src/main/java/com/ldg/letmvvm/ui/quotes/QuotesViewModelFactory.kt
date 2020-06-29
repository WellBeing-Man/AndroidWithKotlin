package com.ldg.letmvvm.ui.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ldg.letmvvm.data.QuoteRepository

class QuotesViewModelFactory(private val quoteRepository: QuoteRepository) :ViewModelProvider.NewInstanceFactory(){

    @Suppress()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuoteViewModel(quoteRepository) as T
        }
}