package com.example.appmaroto.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmaroto.ui.viewmodel.MyAccountViewModel
import com.example.appmaroto.databinding.ActivityMyAccountBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyAccountBinding
    private val viewModel: MyAccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUserEmail().let {
            binding.tvUserEmail.text = it
        }
    }
}