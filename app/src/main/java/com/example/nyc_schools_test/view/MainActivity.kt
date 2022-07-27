package com.example.nyc_schools_test.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nyc_schools_test.common.InternetCheck
import com.example.nyc_schools_test.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
    }



}



