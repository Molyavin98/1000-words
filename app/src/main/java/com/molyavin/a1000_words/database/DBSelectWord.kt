package com.molyavin.a1000_words.database

import android.content.Context
import com.google.firebase.database.*
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.library_words.HelperAdapter

class DBSelectWord(context: Context) : DataBase(context) {

    private var selectW = HashMap<Int, Int>()
    private val itemW = HashMap<Int, Int>()

    private var selectId = ""

    override fun saveDataDB() {

        if (HelperAdapter.selectWords.isNotEmpty()) {
            database.child("idSelectWord").setValue(HelperAdapter.selectWords.keys.toString())
        }
    }

    override fun readData() {

        val eListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                selectId = dataSnapshot.child("idSelectWord").value.toString()

                try {
                    convertingString()
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.addValueEventListener(eListener)
    }

    override fun convertingString() {

        val regex = ","

        var stringSelect: String = selectId.substring(1, selectId.length - 1)
        stringSelect = stringSelect.replace("\\s+".toRegex(), "")
        val listSelect: List<String> = stringSelect.split(regex).toList()

        for (i in listSelect.indices) {
            selectW[listSelect[i].toInt()] = listSelect[i].toInt()
            itemW[listSelect[i].toInt()] = R.drawable.star_on
        }

        HelperAdapter.selectWords = selectW
        HelperAdapter.itemSave = itemW
    }
}