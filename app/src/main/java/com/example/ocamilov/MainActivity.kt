package com.example.ocamilov

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val url = "https://ocamilov.uz/"
    lateinit var progressDialog: ProgressDialog

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
        ikkibtn.setOnClickListener {
            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
        web_view.settings.javaScriptEnabled = true
        if (isNetworkConnected()) {
            progress.visibility = View.GONE
            web_view.visibility = View.VISIBLE
        } else {
            progress.visibility = View.VISIBLE
            web_view.visibility = View.GONE
        }
        web_view.loadUrl(url)
        web_view.setFindListener { i, i2, b ->
            if (b) {
                progress.visibility = View.GONE
                web_view.visibility = View.VISIBLE
            } else {
                progress.visibility = View.VISIBLE
                web_view.visibility = View.GONE
            }
        }
        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                view?.loadUrl(url!!)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (isNetworkConnected()) {
                    progress.visibility = View.GONE
                    web_view.visibility = View.VISIBLE
                } else {
                    progress.visibility = View.VISIBLE
                    web_view.visibility = View.GONE
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (isNetworkConnected()) {
                    progress.visibility = View.GONE
                    web_view.visibility = View.VISIBLE
                } else {
                    progress.visibility = View.VISIBLE
                    web_view.visibility = View.GONE
                }
            }
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}