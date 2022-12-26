package com.molyavin.a1000_words.learning_words

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.databinding.ActivityLearningWordsBinding

class HintWord(
    val context: Context,
    private val engWord: String,
    private val uaWord: String,
    private val ruWord: String,
    private val binding: ActivityLearningWordsBinding
) {


    private var mRewardedAd: RewardedAd? = null


    fun showHint() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {

                DialogInterface.BUTTON_POSITIVE -> {

                    when (binding.dropDown.text.toString()) {

                        context.getString(R.string.language_en) -> {
                            // binding.editTextWord.setText(uaWord)
                            showAds(uaWord)
                        }
                        context.getString(R.string.language_ua) -> {
                            // binding.editTextWord.setText(engWord)
                            showAds(engWord)
                        }
                        context.getString(R.string.language_ru) -> {
                            // binding.editTextWord.setText(engWord)
                            showAds(engWord)
                        }
                    }
                }
                DialogInterface.BUTTON_NEGATIVE -> context.getString(R.string.text_no)
            }
        }

        val dialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(context.getString(R.string.text_prompting))
            .setMessage(context.getString(R.string.text_show_video))
            .setPositiveButton(context.getString(R.string.text_yes), listener)
            .setNegativeButton(context.getString(R.string.text_no), listener)
            .setIcon(R.drawable.bulb)
            .create()

        dialog.show()
    }


    private fun showAds(word: String) {

        MobileAds.initialize(context) {}

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, "ca-app-pub-6890166018762231/5131151602", adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    super.onAdLoaded(rewardedAd)
                    mRewardedAd = rewardedAd
                }
            })

        if (mRewardedAd != null) {
            mRewardedAd?.show(context as Activity, OnUserEarnedRewardListener {
                fun onUserEarnedReward(rewardItem: RewardItem) {
                    binding.editTextWord.setText(word)
                }
            })
        } else {
            Toast.makeText(context, context.getString(R.string.toast_free_word), Toast.LENGTH_SHORT).show()
            binding.editTextWord.setText(word)
        }
    }
}