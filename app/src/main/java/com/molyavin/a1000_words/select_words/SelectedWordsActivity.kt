package com.molyavin.a1000_words.select_words

import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.molyavin.a1000_words.MenuActivity
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.database.DBSelectWord
import com.molyavin.a1000_words.databinding.ActivitySelectedWordsBinding
import com.molyavin.a1000_words.library_words.HelperAdapter.Companion.itemSave
import com.molyavin.a1000_words.library_words.HelperAdapter.Companion.selectWords
import com.molyavin.a1000_words.parse_data.JsonParseWords
import com.molyavin.a1000_words.utility.NetworkChangeListener

class SelectedWordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectedWordsBinding
    private val networkChangeListener: NetworkChangeListener = NetworkChangeListener()
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivitySelectedWordsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView_3)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.layoutManager = layoutManager

        val jPW = JsonParseWords(context = this)
        jPW.jsonData(selectWords)

        val adapter = Adapter(
            jPW.enWordsSelect,
            jPW.trSelect,
            jPW.uaWordsSelect,
            jPW.ruWordsSelect,
            jPW.idSelect,
            this
        )
        binding.recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slideinback, R.anim.slideoutback)
        }

        binding.btnDeletAll.setOnClickListener {
            deleteAllWord()
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

    private fun deleteAllWord() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {

                DialogInterface.BUTTON_POSITIVE -> {
                    selectWords.clear().also { itemSave.clear() }
                    binding.recyclerView.removeAllViewsInLayout()
                    val dbSelectWord = DBSelectWord(context = this)
                    dbSelectWord.database.child("idSelectWord").removeValue()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slideinback, R.anim.slideoutback)
                }
                DialogInterface.BUTTON_NEGATIVE -> getString(R.string.text_no)
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(getString(R.string.text_delete))
            .setMessage(getString(R.string.text_delete_all))
            .setPositiveButton(getString(R.string.text_yes), listener)
            .setNegativeButton(getString(R.string.text_no), listener)
            .setIcon(R.drawable.trash_black)
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
}