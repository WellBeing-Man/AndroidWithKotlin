package com.ldg.letmvvm.data

data class Quote(val quoteText:String,
                val author:String){

    override fun toString(): String ="$quoteText - $author"


}