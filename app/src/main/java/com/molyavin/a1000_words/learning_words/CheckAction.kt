package com.molyavin.a1000_words.learning_words

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Vibrator
import android.widget.Toast
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.databinding.ActivityLearningWordsBinding


class CheckAction(private val binding: ActivityLearningWordsBinding, val context: Context) {

    private val v = (context.getSystemService(VIBRATOR_SERVICE) as Vibrator)

    private fun checkedTranslateEngWord(uaWord: String, ruWord: String) {

        val word: String = binding.editTextWord.text.toString().lowercase()

        if (word == uaWord || word == ruWord) {
            LearningWordsActivity.countCorrectWord++
            binding.btnNextWord.callOnClick()
            attempt = 0
        } else {
            binding.editTextWord.error = context.getString(R.string.not_correct)
            attempt++
            v.vibrate(250)
        }
        binding.correctWord.text = LearningWordsActivity.countCorrectWord.toString()
    }

    private fun checkedTranslateEngWord(en: String) {

        val word: String = binding.editTextWord.text.toString().lowercase()

        if (word == en) {
            LearningWordsActivity.countCorrectWord++
            binding.btnNextWord.callOnClick()
            attempt = 0
        } else {
            binding.editTextWord.error = context.getString(R.string.is_empty)
            attempt++
            v.vibrate(250)
        }
        binding.correctWord.text = LearningWordsActivity.countCorrectWord.toString()
    }

    fun startChecked(enWord: String, uaWord: String, ruWord: String) {

        binding.btnCheck.setOnClickListener {

            if (binding.editTextWord.text.toString().isEmpty()) {
                binding.editTextWord.error = context.getString(R.string.is_empty_edittext)
                v.vibrate(250)
            } else {
                if (binding.dropDown.text.toString() == context.getString(R.string.language_en)) {
                    checkedTranslateEngWord(uaWord, ruWord)
                } else {
                    checkedTranslateEngWord(enWord)
                }
            }
            checkAttempt()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkAttempt() {

        binding.attempt.text = "$attempt${context.getString(R.string.text_attempt)}"

        if (attempt == 3) {
            binding.btnNextWord.callOnClick()
            attempt = 0
            WorkMistakesL.isMistakes = true
            Toast.makeText(context, R.string.toast_push_message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        @JvmStatic
        var attempt: Int = 0
    }
}