package com.ldg.letmvvm.data

class FakeDataBase private constructor(){

    var quoteDao=FakeQuoteDao()
        private set

    companion object{
        @Volatile  private var instance:FakeDataBase?=null

        fun getInstance()=
            instance?: synchronized(lock = this){
                instance?: FakeDataBase().also { instance=it }
            }
    }
}