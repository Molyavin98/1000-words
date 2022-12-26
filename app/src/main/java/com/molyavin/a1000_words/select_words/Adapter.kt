package com.molyavin.a1000_words.select_words

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.molyavin.a1000_words.R
import com.molyavin.a1000_words.library_words.HelperAdapter
import com.molyavin.a1000_words.voice.TextToSpeech

class Adapter(
    var enWords: ArrayList<String>,
    var tr: ArrayList<String>,
    var uaWords: ArrayList<String>,
    var ruWords: ArrayList<String>,
    var id: ArrayList<String>,
    var context: Context
) : RecyclerView.Adapter<Adapter.MyViewClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewClass {

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_select, parent, false)

        return MyViewClass(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: MyViewClass,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.en.text = "${context.getString(R.string.enFlag)} " + enWords[position] + " " + tr[position]
        holder.ua.text = "${context.getString(R.string.uaFlag)} " + uaWords[position]
        holder.ru.text = "${context.getString(R.string.ruFlag)} " + ruWords[position]
        holder.id.text = id[position]

        holder.btnVoice.setOnClickListener {
            val textToSpeech = TextToSpeech(enWords[position], this.context)
            textToSpeech.speakOut()
        }
    }

    override fun getItemCount(): Int {
        return id.size
    }


    fun deleteItem(pos: Int) {
        val idItem: Int = id[pos].toInt()
        id.removeAt(pos)
        notifyItemRemoved(pos)
        HelperAdapter.itemSave.remove(idItem - 1)
        HelperAdapter.selectWords.remove(idItem - 1)
    }

    class MyViewClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var en: TextView
        var ua: TextView
        var ru: TextView
        var id: TextView
        var btnVoice: ImageButton
        private var cardView: CardView

        init {
            en = itemView.findViewById(R.id.word)
            ua = itemView.findViewById(R.id.uaWord)
            ru = itemView.findViewById(R.id.ruWord)
            id = itemView.findViewById(R.id.id)

            cardView = itemView.findViewById(R.id.cardView)
            btnVoice = itemView.findViewById(R.id.btn_voice)
        }
    }
}