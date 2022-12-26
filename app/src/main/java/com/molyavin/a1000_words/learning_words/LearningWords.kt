package com.molyavin.a1000_words.learning_words

import android.content.Context
import android.content.Intent
import com.molyavin.a1000_words.databinding.ActivityLearningWordsBinding
import com.molyavin.a1000_words.parse_data.JsonParseWords
import kotlin.random.Random
import kotlin.random.nextInt

class LearningWords(
    private var binding: ActivityLearningWordsBinding,
    var parseWords: JsonParseWords,
    var checkedTranslate: CheckAction,
    val intent: Intent,
    val context: Context
) : ALearning() {

    private var id: Int = 0

    override fun word() {

        val num1 = intent.getIntExtra("Num1", 0)
        val num2 = intent.getIntExtra("Num2", 0)

        parseWords.jsonData()

        val range: IntRange = num1..num2
        val index: Int = Random.nextInt(range)

        createWord(binding.dropDown, index,binding,parseWords, context)

        id = index
        checkCorrect()

        hint(index,binding,parseWords,context)
    }

    override fun checkCorrect() {

        checkedTranslate.startChecked(
            parseWords.enWords[id], parseWords.uaWords[id],
            parseWords.ruWords[id]
        )

        if (WorkMistakesL.isMistakes){
            LearningWordsActivity.wordsMistakes[id] = id
                WorkMistakesL.isMistakes = false
        }
    }
}