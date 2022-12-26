package com.molyavin.a1000_words.learning_words

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.databinding.ActivityLearningWordsBinding
import com.molyavin.a1000_words.parse_data.JsonParseWords
import com.molyavin.a1000_words.utility.NetworkChangeListener

class LearningWordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearningWordsBinding
    private val networkChangeListener: NetworkChangeListener = NetworkChangeListener()
    private lateinit var parseWords: JsonParseWords
    private lateinit var checkedTranslate: CheckAction

    private lateinit var learningWordL: LearningWords
    private lateinit var selectWordL: SelectWordL
    private lateinit var mistakesL: WorkMistakesL

    private lateinit var mAdView: AdView

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityLearningWordsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView_5)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        adListener()

        parseWords = JsonParseWords(context = this)
        checkedTranslate = CheckAction(binding, context = this)

        val language = resources.getStringArray(R.array.language)
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, language)
        binding.dropDown.setAdapter(adapter)

        onClickListener()

    }

    private fun adListener(){

        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {}

            override fun onAdClosed() {}

            override fun onAdFailedToLoad(adError: LoadAdError) {}

            override fun onAdImpression() {}

            override fun onAdLoaded() {}

            override fun onAdOpened() {}
        }
    }

    private fun startLearning() {
        if (intent.getIntExtra("Learning select", 0) == 1) {
            selectWordL = SelectWordL(binding, parseWords, checkedTranslate, intent, this)
            selectWordL.word()
        } else if (intent.getIntExtra("Learning work mistakes", 0) == 1) {
            mistakesL = WorkMistakesL(binding, parseWords, checkedTranslate, intent, this)
            mistakesL.word()
        } else {
            learningWordL = LearningWords(binding, parseWords, checkedTranslate, intent, this)
            learningWordL.word()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onClickListener() {

        binding.btnBack.setOnClickListener {
            showCorrectAnswer()
        }

        binding.btnNextWord.setOnClickListener {
            binding.editTextWord.setText("")
            binding.attempt.text = getString(R.string.text_attempt_string)
            binding.editTextWord.error = null
            CheckAction.attempt = 0
            startLearning()
        }
    }

    private fun showCorrectAnswer() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {

                DialogInterface.BUTTON_POSITIVE -> {

                    val intent = Intent(this, MenuLearningWordsActivity::class.java)
                    countCorrectWord = 0
                    startActivity(intent)
                    overridePendingTransition(R.anim.slideinback, R.anim.slideoutback)
                }
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(getString(R.string.text_title))
            .setMessage(String.format(getString(R.string.text_message, countCorrectWord)))
            .setPositiveButton(getString(R.string.text_yes), listener)
            .setIcon(R.drawable.notebook_pencil)
            .create()

        dialog.show()
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


    companion object {

        @JvmStatic
        var countCorrectWord: Int = 0

        @JvmStatic
        var wordsMistakes = HashMap<Int, Int>()
    }
}
