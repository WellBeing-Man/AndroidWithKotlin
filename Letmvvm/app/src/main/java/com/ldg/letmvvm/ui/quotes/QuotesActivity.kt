package com.ldg.letmvvm.ui.quotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ldg.letmvvm.R
import com.ldg.letmvvm.data.Quote
import com.ldg.letmvvm.utility.ObjectInjector
import kotlinx.android.synthetic.main.activity_quotes.*
import java.lang.StringBuilder

class QuotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)

        initailUi()

    }

    private fun initailUi(){
        val factory=ObjectInjector.provideQuoteViewModelFacotory()
        val viewModel= ViewModelProviders.of(this,factory)
            .get(QuoteViewModel::class.java)
        viewModel.getQuotes().observe(this, Observer { quotes->
            val stringBuilder=StringBuilder()
            quotes.forEach{
                quote->stringBuilder.append("$quote\n\n")
            }
            textView_quotes.text=stringBuilder.toString()
        })

        button_add_quote.setOnClickListener {
            val quote=Quote(editText_quote.text.toString(),editText_author.text.toString())
            viewModel.addQuotes(quote)
            editText_quote.setText("")
            editText_author.setText("")
        }
    }
}
