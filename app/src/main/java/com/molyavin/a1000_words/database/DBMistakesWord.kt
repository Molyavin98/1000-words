package com.molyavin.a1000_words.database

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.molyavin.a1000_words.learning_words.LearningWordsActivity.Companion.wordsMistakes

class DBMistakesWord(context: Context) : DataBase(context) {

    private var wMistakes = HashMap<Int, Int>()
    private var wMistakesId = ""

    override fun saveDataDB() {
        if (wordsMistakes.isNotEmpty()) {
            database.child("wordsMistakes").setValue(wordsMistakes.keys.toString())
        }
    }

    override fun readData() {

        val eListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                wMistakesId = dataSnapshot.child("wordsMistakes").value.toString()

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

        var stringMistakes: String = wMistakesId.substring(1, wMistakesId.length - 1)
        stringMistakes = stringMistakes.replace("\\s+".toRegex(), "")
        val listSelect: List<String> = stringMistakes.split(regex).toList()

        for (i in listSelect.indices) {
            wMistakes[listSelect[i].toInt()] = listSelect[i].toInt()
        }

        wordsMistakes = wMistakes
        Log.d("mistakes1", wordsMistakes.toString())
        Log.d("mistakes2", wMistakes.toString())
    }
}