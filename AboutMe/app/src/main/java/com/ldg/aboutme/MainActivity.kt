package com.ldg.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.ldg.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val myName:MyName= MyName("LDG")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.myName=myName

    //    findViewById<Button>(R.id.done_button).setOnClickListener {
      //      addNickname(it)
        //}
    binding.doneButton.setOnClickListener { addNickname(it) }
    }

    private fun addNickname(view:View){


        binding.apply {
         myName?.nickname=nicknameEdit.text.toString()
            invalidateAll()
         binding.nicknameEdit.visibility=View.GONE
         binding.doneButton.visibility=View.GONE
         binding.nicknameText.visibility=View.VISIBLE
        }
        //hide button
        val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}
