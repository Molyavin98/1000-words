package com.molyavin.a1000_words

import android.annotation.SuppressLint

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.molyavin.a1000_words.database.DBMistakesWord
import com.molyavin.a1000_words.database.DBSelectWord
import com.molyavin.a1000_words.databinding.ActivitySplashScreenBinding
import com.molyavin.a1000_words.utility.NetworkChangeListener


@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivitySplashScreenBinding
private lateinit var animationForText: Animation
private lateinit var animForImageAndButton: Animation

private val networkChangeListener: NetworkChangeListener = NetworkChangeListener()

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivitySplashScreenBinding.inflate(layoutInflater).also { setContentView(it.root) }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        /** Animation realization */
        animForImageAndButton = AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.anim)
        animationForText =
            AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.anim_increase)
        binding.imageBook.animation = animForImageAndButton
        binding.textSplashScreen.animation = animationForText
        binding.btnStart.animation = animForImageAndButton


        /** BaseData object */
        val dB = DBSelectWord(context = this)
        dB.readData()

        val dbMistakes = DBMistakesWord(context = this)
        dbMistakes.readData()

        binding.btnStart.setOnClickListener {
                val intent = Intent(this@SplashScreenActivity, MenuActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.anim_alpha_in, R.anim.anim_alpha_out)
                finish()
        }
    }

    override fun onStart() {
        val filter = IntentFilter(CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener,filter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(networkChangeListener)
        super.onStop()
    }
}