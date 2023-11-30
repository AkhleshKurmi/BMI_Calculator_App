package com.example.bmi_calculator

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.Paint.Style
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.example.bmi_calculator.databinding.ActivityAboutBmiBinding

class AboutBMI : AppCompatActivity() {

    lateinit var bindingAboutBMI : ActivityAboutBmiBinding
    lateinit var progressBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAboutBMI = ActivityAboutBmiBinding.inflate(layoutInflater)
        setContentView(bindingAboutBMI.root)
        progressBar = ProgressDialog(this@AboutBMI)
        progressBar.setMessage("Loading, Please wait...")
        progressBar.setTitle("Loading")
        progressBar.setCancelable(false)
        progressBar.show()
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        webViewAboutBMI()
    }

    fun webViewAboutBMI(){
        if (bindingAboutBMI.wvAboutBmi != null){
            val webSettings = bindingAboutBMI.wvAboutBmi!!.settings
            webSettings.javaScriptEnabled = true

            bindingAboutBMI.wvAboutBmi!!.webViewClient = WebViewClient()
            bindingAboutBMI.wvAboutBmi!!.webChromeClient = WebChromeClient()
            bindingAboutBMI.wvAboutBmi!!.loadUrl("https://en.wikipedia.org/wiki/Body_mass_index")
            bindingAboutBMI.wvAboutBmi!!.webViewClient = object: WebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressBar.show()
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressBar.dismiss()

                    super.onPageFinished(view, url)
                }
            }
        }

    }

    override fun onBackPressed() {
        if (bindingAboutBMI.wvAboutBmi!!.canGoBack()){
            bindingAboutBMI.wvAboutBmi!!.goBack()
        }else {
            super.onBackPressed()
        }
    }

}