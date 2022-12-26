package com.molyavin.a1000_words.learning_words

import android.content.Context
import android.content.Intent
import com.molyavin.a1000_words.databinding.ActivityLearningWordsBinding
import com.molyavin.a1000_words.library_words.HelperAdapter.Companion.selectWords
import com.molyavin.a1000_words.parse_data.JsonParseWords

class SelectWordL(
    private var binding: ActivityLearningWordsBinding,
    private var parseWords: JsonParseWords,
    private var checkedTranslate: CheckAction,
    val intent: Intent,
    val context: Context
) : ALearning() {

    private val index: Int = selectWords.keys.random()

    override fun word() {

        parseWords.jsonData(selectWords)

        createWord(binding.dropDown, index, binding, parseWords, context)

        checkCorrect()

        hint(index, binding, parseWords, context)
    }

    override fun checkCorrect() {

        checkedTranslate.startChecked(
            parseWords.enWords[index], parseWords.uaWords[index],
            parseWords.ruWords[index]
        )
    }
}