package com.molyavin.a1000_words

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.*
import com.molyavin.a1000_words.database.DBSelectWord
import com.molyavin.a1000_words.databinding.ActivityMenuBinding
import com.molyavin.a1000_words.learning_words.MenuLearningWordsActivity
import com.molyavin.a1000_words.library_words.LibraryActivity
import com.molyavin.a1000_words.select_words.SelectedWordsActivity
import com.molyavin.a1000_words.utility.NetworkChangeListener
import com.molyavin.a1000_words.voice.TextToSpeech

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private val networkChangeListener: NetworkChangeListener = NetworkChangeListener()
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater).also { setContentView(it.root) }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //Google adMob
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView_1)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        binding.btnLibraryWords.setOnClickListener {
            val intent = Intent(this, LibraryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slidein, R.anim.slideout)

        }

        binding.btnLearningWords.setOnClickListener {
            val intent = Intent(this, MenuLearningWordsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }

        binding.btnSelectedWords.setOnClickListener {
            val intent = Intent(this, SelectedWordsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }

        binding.btnVoice.setOnClickListener {
            val textToSpeech = TextToSpeech(binding.englishText.text.toString(), this)
            textToSpeech.speakOut()

        }

        val dbSelectWord = DBSelectWord(context = this)
        dbSelectWord.saveDataDB()

        //Google adMob listener
        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {}

            override fun onAdClosed() {}

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("ErrorAdMobb",adError.toString())
            }

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