package com.molyavin.a1000_words.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class TextToSpeech(var text: String, var context: Context){

    private lateinit var tts: TextToSpeech

    fun speakOut(){
        tts = TextToSpeech(context, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS){
                tts.language = Locale.US
                tts.setSpeechRate(0.70f)
                tts.speak(text,TextToSpeech.QUEUE_FLUSH,null)
            }
        })
    }
}