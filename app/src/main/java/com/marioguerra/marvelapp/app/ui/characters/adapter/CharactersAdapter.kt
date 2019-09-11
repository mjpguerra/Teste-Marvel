package com.marioguerra.marvelapp.app.ui.characters.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marioguerra.marvelapp.R
import com.marioguerra.marvelapp.app.base.recycler.PaginationAdapter
import com.marioguerra.marvelapp.app.ui.utils.addButtonAnimation
import com.marioguerra.marvelapp.data.model.character.Character
import com.squareup.picasso.Picasso

class CharactersAdapter(
    context: Context,
    diffCallback: CharacterDiffCallback,
    private val characterListener: CharacterListener
) : PaginationAdapter<Character>(context, diffCallback) {

    interface CharacterListener {
        fun onCharacterClick(character: Character)
    }

    override fun getViewTypeForItem(position: Int): Int {
        return R.layout.item_character
    }

    override fun onCreateViewHolderForItem(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_character, parent, false)
        val holder = CharacterViewHolder(view)
        holder.itemView.setOnClickListener {
            val character = getItem(holder.adapterPosition)
            if (character != null) {
                characterListener.onCharacterClick(character)
            }
        }
        return holder
    }

    override fun onBindViewHolderForItem(holder: RecyclerView.ViewHolder, position: Int) {
        val character = getItem(position)
        if (character != null && holder is CharacterViewHolder) {
            holder.bind(character)
        }
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCharacterAvatar: ImageView = itemView.findViewById(R.id.ivCharacterAvatar)
        private val tvCharacterName: TextView = itemView.findViewById(R.id.tvCharacterName)
        private val card: CardView = itemView.findViewById(R.id.card)

        fun bind(character: Character) {
           // Glide.with(ivCharacterAvatar)
            //    .load(character.image?.getFullPath())
            //    .fitCenter()
            //    .centerCrop()
             //   .into(ivCharacterAvatar)


            card.addButtonAnimation()

            Picasso.get().load(character.image?.getFullPath()).fit().into(ivCharacterAvatar)

            tvCharacterName.text = character.name
        }
    }
}