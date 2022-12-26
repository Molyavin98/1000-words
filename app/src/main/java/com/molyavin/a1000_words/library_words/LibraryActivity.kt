package com.molyavin.a1000_words.library_words

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.molyavin.a1000_words.MenuActivity
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.databinding.ActivityLibraryBinding
import com.molyavin.a1000_words.parse_data.JsonParseWords
import com.molyavin.a1000_words.utility.NetworkChangeListener

class LibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLibraryBinding
    private val networkChangeListener: NetworkChangeListener = NetworkChangeListener()

    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater).also { setContentView(it.root) }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView_2)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.layoutManager = layoutManager

        val jPW = JsonParseWords(context = this)
        jPW.jsonData()

        val helperAdapter =
            HelperAdapter(jPW.enWords, jPW.tr, jPW.uaWords, jPW.ruWords, jPW.id, this)
        binding.recyclerView.adapter = helperAdapter


        binding.btnTop.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0);
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slideinback, R.anim.slideoutback)
        }

        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {}

            override fun onAdClosed() {}

            override fun onAdFailedToLoad(adError: LoadAdError) {}

            override fun onAdImpression() {}

            override fun onAdLoaded() {}

            override fun onAdOpened() {}
        }
    }

    override fun onStart() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener, filter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(networkChangeListener)
        super.onStop()
    }

    override fun onBackPressed() {}
}