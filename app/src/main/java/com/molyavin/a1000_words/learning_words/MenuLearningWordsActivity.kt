package com.molyavin.a1000_words.learning_words

import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.*
import com.molyavin.a1000_words.MenuActivity
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.database.DBMistakesWord
import com.molyavin.a1000_words.learning_words.LearningWordsActivity.Companion.wordsMistakes
import com.molyavin.a1000_words.library_words.HelperAdapter
import com.molyavin.a1000_words.utility.NetworkChangeListener

class MenuLearningWordsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var lesson1: Button
    private lateinit var lesson2: Button
    private lateinit var lesson3: Button
    private lateinit var lesson4: Button
    private lateinit var lesson5: Button
    private lateinit var lesson6: Button
    private lateinit var lesson7: Button
    private lateinit var lesson8: Button
    private lateinit var lesson9: Button
    private lateinit var lesson10: Button
    private lateinit var btnLearningSelectW: Button
    private lateinit var btnBack: ImageButton
    private lateinit var btnInfo: ImageButton
    private lateinit var btnWorkMistakes: Button

    private val networkChangeListener: NetworkChangeListener = NetworkChangeListener()
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_learning_words)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        init()

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView_4)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        adListener()

        btnBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slideinback, R.anim.slideoutback)
        }

        btnInfo.setOnClickListener {showInfo()}

        val dbMistakes = DBMistakesWord(context = this)
        dbMistakes.saveDataDB()


    }

    private fun init() {

        lesson1 = findViewById(R.id.les1)
        lesson2 = findViewById(R.id.les2)
        lesson3 = findViewById(R.id.les3)
        lesson4 = findViewById(R.id.les4)
        lesson5 = findViewById(R.id.les5)
        lesson6 = findViewById(R.id.les6)
        lesson7 = findViewById(R.id.les7)
        lesson8 = findViewById(R.id.les8)
        lesson9 = findViewById(R.id.les9)
        lesson10 = findViewById(R.id.les10)
        btnBack = findViewById(R.id.btn_back)
        btnInfo = findViewById(R.id.btn_info)
        btnLearningSelectW = findViewById(R.id.btnLearningSelect)
        btnWorkMistakes = findViewById(R.id.btnWorkMistakes)

        lesson1.setOnClickListener(this)
        lesson2.setOnClickListener(this)
        lesson3.setOnClickListener(this)
        lesson4.setOnClickListener(this)
        lesson5.setOnClickListener(this)
        lesson6.setOnClickListener(this)
        lesson7.setOnClickListener(this)
        lesson8.setOnClickListener(this)
        lesson9.setOnClickListener(this)
        lesson10.setOnClickListener(this)
        btnLearningSelectW.setOnClickListener(this)
        btnWorkMistakes.setOnClickListener(this)
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

    override fun onClick(view: View?) {

        val intent: Intent

        when (view?.id) {

            R.id.les1 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 0)
                intent.putExtra("Num2", 99)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.les2 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 100)
                intent.putExtra("Num2", 199)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.les3 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 200)
                intent.putExtra("Num2", 299)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.les4 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 300)
                intent.putExtra("Num2", 399)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.les5 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 400)
                intent.putExtra("Num2", 499)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.les6 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 500)
                intent.putExtra("Num2", 599)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.les7 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 600)
                intent.putExtra("Num2", 699)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.les8 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 700)
                intent.putExtra("Num2", 799)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.les9 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 800)
                intent.putExtra("Num2", 899)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.les10 -> {
                intent = Intent(this, LearningWordsActivity::class.java)
                intent.putExtra("Num1", 900)
                intent.putExtra("Num2", 1000)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein, R.anim.slideout)
            }

            R.id.btnLearningSelect -> {
                if (HelperAdapter.selectWords.isEmpty()) {
                    Toast.makeText(
                        this, getString(R.string.toast_select),
                        Toast.LENGTH_LONG
                    ).show()
                }else {
                    intent = Intent(this, LearningWordsActivity::class.java)
                    intent.putExtra("Learning select", 1)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slidein, R.anim.slideout)
                }
            }

            R.id.btnWorkMistakes ->{

                if (wordsMistakes.isEmpty()){
                    Toast.makeText(
                        this, getString(R.string.toast_mistakes),
                        Toast.LENGTH_LONG
                    ).show()
                }else {
                    intent = Intent(this, LearningWordsActivity::class.java)
                    intent.putExtra("Learning work mistakes", 1)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slidein, R.anim.slideout)
                }
            }
        }
    }

    override fun onStart() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener,filter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(networkChangeListener)
        super.onStop()
    }


    private fun showInfo() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {

                DialogInterface.BUTTON_POSITIVE -> {}
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(getString(R.string.text_title_info))
            .setMessage(String.format(getString(R.string.text_message_info)))
            .setPositiveButton(getString(R.string.text_yes), listener)
            .setIcon(R.drawable.notebook_pencil)
            .create()

        dialog.show()
    }

    override fun onBackPressed() {}
}