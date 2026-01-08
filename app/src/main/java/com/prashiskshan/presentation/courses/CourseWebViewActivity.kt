package com.prashiskshan.presentation.courses

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.prashiskshan.databinding.ActivityCourseWebviewBinding

class CourseWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseWebviewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title") ?: "Course"
        val url = intent.getStringExtra("url") ?: "https://example.com"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: android.webkit.WebView?, newProgress: Int) {
                binding.progress.isVisible = newProgress in 0..99
            }
        }
        binding.webView.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
            return false
        }
        finish()
        return true
    }
}


