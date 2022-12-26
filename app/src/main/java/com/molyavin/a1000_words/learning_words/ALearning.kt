package com.molyavin.a1000_words.learning_words

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.databinding.ActivityLearningWordsBinding
import com.molyavin.a1000_words.parse_data.JsonParseWords

abstract class ALearning {

    abstract fun word()

    @SuppressLint("SetTextI18n")
    fun createWord(
        dropDown: EditText, index: Int, binding: ActivityLearningWordsBinding,
        parseWords: JsonParseWords, context: Context
    ) {

        when (dropDown.text.toString()) {

            context.getString(R.string.language_en) -> {
                binding.word.text = ""
                binding.word.text =
                    context.getString(R.string.enFlag) + parseWords.enWords[index]
                binding.outlinedTextField.hint = context.getString(R.string.text_enter_ua_ru)
            }

            context.getString(R.string.language_ua) -> {
                binding.word.text = ""
                binding.word.text =
                    context.getString(R.string.uaFlag) + parseWords.uaWords[index]
                binding.outlinedTextField.hint = context.getString(R.string.text_enter_eng)
            }

            context.getString(R.string.language_ru) -> {
                binding.word.text = ""
                binding.word.text =
                    context.getString(R.string.ruFlag) + parseWords.ruWords[index]
                binding.outlinedTextField.hint = context.getString(R.string.text_enter_eng)
            }
        }
    }

    fun hint(
        index: Int,
        binding: ActivityLearningWordsBinding,
        parseWords: JsonParseWords,
        context: Context
    ) {
        binding.btnHint.setOnClickListener {
            HintWord(
                context, parseWords.enWords[index],
                parseWords.uaWords[index], binding
            ).showHint()
        }
    }

    abstract fun checkCorrect()
}