package com.inhyuck.examples.kotlinnews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.inhyuck.examples.kotlinnews.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragmentContainer).popBackStack()
        return super.onSupportNavigateUp()
    }
}