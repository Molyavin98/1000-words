package com.molyavin.a1000_words.library_words

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
import com.molyavin.a1000_words.voice.TextToSpeech

class HelperAdapter(
    var enWords: ArrayList<String>,
    var tr: ArrayList<String>,
    var uaWords: ArrayList<String>,
    var ruWords: ArrayList<String>,
    var id: ArrayList<String>,
    var context: Context,
) : RecyclerView.Adapter<HelperAdapter.MyViewClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewClass {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)

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

        holder.bind(position)

        holder.btnVoice.setOnClickListener {
            val textToSpeech = TextToSpeech(enWords[position], this.context)
            textToSpeech.speakOut()
        }
    }

    override fun getItemCount(): Int {
        return id.size
    }

    class MyViewClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var en: TextView
        var ua: TextView
        var ru: TextView
        var id: TextView
        var btnVoice: ImageButton
         var btnAddToFavorites: ImageButton
        private var cardView: CardView

        private val imageOn: Int = R.drawable.star_on
        private val imageOff: Int = R.drawable.star_off

        init {
            en = itemView.findViewById(R.id.word)
            ua = itemView.findViewById(R.id.uaWord)
            ru = itemView.findViewById(R.id.ruWord)
            id = itemView.findViewById(R.id.id)
            btnAddToFavorites = itemView.findViewById(R.id.btn_addTo_favorites)
            cardView = itemView.findViewById(R.id.cardView)
            btnVoice = itemView.findViewById(R.id.btn_voice)

            btnAddToFavorites.setOnClickListener {
                btnAddToFavoritesClick()
            }
        }

        private fun btnAddToFavoritesClick() {

            if (itemSave[adapterPosition] != imageOn) {
                btnAddToFavorites.setImageResource(imageOn)
                itemSave[adapterPosition] = imageOn
                selectWords[adapterPosition] = adapterPosition
            } else {
                btnAddToFavorites.setImageResource(imageOff)
                itemSave[adapterPosition] = imageOff
                selectWords.remove(adapterPosition)
            }
        }

        fun bind(position: Int) {

            if (itemSave[position] != imageOn) {
                btnAddToFavorites.setImageResource(imageOff)
            } else {
                btnAddToFavorites.setImageResource(imageOn)
            }
        }
    }

    companion object {

        @JvmStatic
        var itemSave = HashMap<Int, Int>()
        @JvmStatic
        var selectWords = HashMap<Int, Int>()
    }

}