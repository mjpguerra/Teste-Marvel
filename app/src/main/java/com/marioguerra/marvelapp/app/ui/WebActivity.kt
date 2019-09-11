package com.marioguerra.marvelapp.app.ui


import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.marioguerra.marvelapp.R
import com.marioguerra.marvelapp.app.base.hide
import com.marioguerra.marvelapp.app.base.show
import kotlinx.android.synthetic.main.characters_fragment.*
import kotlinx.android.synthetic.main.layout.*
import kotlinx.android.synthetic.main.layout.pbLoading
import kotlinx.android.synthetic.main.layout.toolbar

/**
 * @author Mario Guerra on 11/09/2019
 */

class WebActivity : AppCompatActivity() {


        var mywebview: WebView? = null

        var url : String = "https://www.marvel.com/"

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.layout)

            mywebview = findViewById<WebView>(R.id.webview)
            mywebview!!.settings.javaScriptEnabled = true

            mywebview!!.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    view?.loadUrl(url)
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }

            toolbar.title = url

            mywebview!!.loadUrl(url)

            // Get the web view settings instance
            val settings = mywebview!!.settings

            // Enable java script in web view
            settings.javaScriptEnabled = true

            // Enable and setup web view cache
            settings.setAppCacheEnabled(true)
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.setAppCachePath(cacheDir.path)


            // Enable zooming in web view
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = true


            // Zoom web view text
            settings.textZoom = 125


            // Enable disable images in web view
            settings.blockNetworkImage = false
            // Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true


            // More web view settings
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true  // api 26
            }
            //settings.pluginState = WebSettings.PluginState.ON
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false


            // More optional settings, you can enable it by yourself
            settings.domStorageEnabled = true
            settings.setSupportMultipleWindows(true)
            settings.loadWithOverviewMode = true
            settings.allowContentAccess = true
            settings.setGeolocationEnabled(true)
            settings.allowUniversalAccessFromFileURLs = true
            settings.allowFileAccess = true

            // WebView settings
            mywebview!!.fitsSystemWindows = true


            /*
                if SDK version is greater of 19 then activate hardware acceleration
                otherwise activate software acceleration
            */
            mywebview!!.setLayerType(View.LAYER_TYPE_HARDWARE, null)


            // Set web view client
            mywebview!!.webViewClient = object: WebViewClient(){
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                    // Page loading started
                    // Do something
                    pbLoading.show()
                    toast("Carregando a pagina, por favor espere...")

                }

                override fun onPageFinished(view: WebView, url: String) {
                    // Page loading finished
                    // Display the loaded page title in a toast message
                    pbLoading.hide()
                    toast("${view.title}")

                }
            }


            // Set web view chrome client
            mywebview!!.webChromeClient = object: WebChromeClient(){
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    //progress_bar.progress = newProgress
                }
            }


        }

    // Extension function to show toast message
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
