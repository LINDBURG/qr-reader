package com.linbug.qrreader

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.linbug.qrreader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val urlViewModel: UrlViewModel by viewModels {
        UrlViewModelFactory((application as UrlsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() {
        binding.button.setOnClickListener {
            IntentIntegrator(this).apply {
                setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                setPrompt("QR 코드 스캔")
                setCameraId(0)
                setOrientationLocked(false)
                initiateScan()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        result?.let {
            it.contents?.let {
                Toast.makeText(this, "scanned" + result.contents, Toast.LENGTH_LONG).show()

                binding.webView.webViewClient = WebViewClient()
                binding.webView.loadUrl(result.contents)

                val replyIntent = Intent()
                    replyIntent.putExtra(EXTRA_REPLY, result.contents)
                    setResult(Activity.RESULT_OK, replyIntent)

                val url = Url(result.contents)
                urlViewModel.insert(url)



            } ?: run {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            }
        } ?: run {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_history) {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.qrreader.REPLY"
    }

}