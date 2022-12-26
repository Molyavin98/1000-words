package com.molyavin.a1000_words.parse_data

import android.content.Context

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream

class JsonParseWords(var context: Context) {

    var id = ArrayList<String>()
    var enWords = ArrayList<String>()
    var tr = ArrayList<String>()
    var uaWords = ArrayList<String>()
    var ruWords = ArrayList<String>()

    var idSelect = ArrayList<String>()
    var enWordsSelect = ArrayList<String>()
    var trSelect = ArrayList<String>()
    var uaWordsSelect = ArrayList<String>()
    var ruWordsSelect = ArrayList<String>()

    //Method parse data from json file
    fun jsonData() {

        try {
            val jsonObject = JSONObject(jsonDataFromAsset("1000-Words-English.json")!!)
            val jsonArray: JSONArray = jsonObject.getJSONArray("words")

            for (i in 0..jsonArray.length()) {

                val wordsData: JSONObject = jsonArray.getJSONObject(i)
                id.add(wordsData.getString("ID"))
                enWords.add(wordsData.getString("ENG"))
                tr.add(wordsData.getString("TR"))
                uaWords.add(wordsData.getString("UA"))
                ruWords.add(wordsData.getString("RU"))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun jsonData(hashMap: HashMap<Int, Int>) {

        jsonData()

        for (i in hashMap) {
            idSelect.add(id[i.key])
            enWordsSelect.add(enWords[i.key])
            trSelect.add(tr[i.key])
            uaWordsSelect.add(uaWords[i.key])
            ruWordsSelect.add(ruWords[i.key])

        }
    }

    //Method for open file from asset package
    private fun jsonDataFromAsset(fileName: String): String? {

        var json: String? = null
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val sizeOfFile: Int = inputStream.available()
            val bufferData = ByteArray(sizeOfFile)
            inputStream.read(bufferData)
            inputStream.close()
            json = String(bufferData, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return json
    }
}