package com.wangxingxing.xtoastdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wangxingxing.xtoastdemo.databinding.ActivityMainBinding
import com.wangxingxing.xtoastdemo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShow.setOnClickListener {

        }
    }
}