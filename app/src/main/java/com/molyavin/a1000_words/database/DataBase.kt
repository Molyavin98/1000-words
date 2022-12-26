package com.molyavin.a1000_words.database

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


abstract class DataBase(context: Context) {


    @SuppressLint("HardwareIds")
    private var id: String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    var database: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("user").child(id)

    abstract fun saveDataDB()

    abstract fun readData()

    abstract fun convertingString()
}






