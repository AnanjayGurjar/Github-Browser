package com.ananjay.githubbrowser

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ananjay.githubbrowser.ui.viewmodels.RepositoryViewModel

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var viewModel: RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        ).get(RepositoryViewModel::class.java)
//
//        viewModel.getRepo("jquery", "jquery")
//        viewModel.myRepo.observe(this) {
//            Log.d(TAG, "onCreate: ${it.archive_url}")
//        }

    }



}