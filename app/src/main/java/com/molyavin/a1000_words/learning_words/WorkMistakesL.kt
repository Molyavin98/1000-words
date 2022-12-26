package com.molyavin.a1000_words.learning_words

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.database.DBMistakesWord
import com.molyavin.a1000_words.databinding.ActivityLearningWordsBinding
import com.molyavin.a1000_words.learning_words.LearningWordsActivity.Companion.wordsMistakes
import com.molyavin.a1000_words.parse_data.JsonParseWords

class WorkMistakesL(
    private var binding: ActivityLearningWordsBinding,
    private var parseWords: JsonParseWords,
    private var checkedTranslate: CheckAction,
    val intent: Intent,
    val context: Context
) : ALearning() {

    private val index: Int = wordsMistakes.keys.random()

    override fun word() {

        parseWords.jsonData(wordsMistakes)

        createWord(binding.dropDown, index, binding, parseWords, context)

        checkCorrect()

        hint(index, binding, parseWords, context)
    }

    override fun checkCorrect() {

        checkedTranslate.startChecked(
            parseWords.enWords[index], parseWords.uaWords[index],
            parseWords.ruWords[index]
        )

        if (LearningWordsActivity.countCorrectWord == wordsMistakes.size) {
            binding.btnBack.callOnClick()
            wordsMistakes.clear()
            val db = DBMistakesWord(context)
            db.database.child("wordsMistakes").removeValue()

            Toast.makeText(
                context,
                context.getString(R.string.toast_mistakes_done),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        @JvmStatic
        var isMistakes: Boolean = false
    }
}