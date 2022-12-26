package com.molyavin.a1000_words.learning_words

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.*
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
    private var isLoading = false


    fun showHint() {

        MobileAds.initialize(context) {}
        loadRewardedAd()

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {

                DialogInterface.BUTTON_POSITIVE -> {

                    when (binding.dropDown.text.toString()) {

                        context.getString(R.string.language_en) -> {
                            binding.editTextWord.setText(uaWord)
                            showRewardedVideo()
                        }
                        context.getString(R.string.language_ua) -> {
                            binding.editTextWord.setText(engWord)
                            showRewardedVideo()
                        }
                        context.getString(R.string.language_ru) -> {
                            binding.editTextWord.setText(engWord)
                            showRewardedVideo()
                        }
                    }
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    context.getString(R.string.text_no)
                }
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

    private fun loadRewardedAd() {
        if (mRewardedAd == null) {
            isLoading = true
            val adRequest = AdRequest.Builder().build()

            RewardedAd.load(context, context.getString(R.string.google_hint_key), adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        isLoading = false
                        mRewardedAd = null
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        mRewardedAd = ad
                        isLoading = false
                    }
                }
            )
        }
    }


    private fun showRewardedVideo() {

        if (mRewardedAd != null) {
            mRewardedAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        mRewardedAd = null
                        loadRewardedAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        mRewardedAd = null
                    }

                    override fun onAdShowedFullScreenContent() {}
                }

            mRewardedAd?.show(
                context as Activity,
                OnUserEarnedRewardListener() {
                    fun onUserEarnedReward(rewardItem: RewardItem) {}
                }
            )
        }
    }
}