package com.marioguerra.marvelapp.app.ui.characters.adapter

import androidx.recyclerview.widget.DiffUtil
import com.marioguerra.marvelapp.data.model.character.Character

class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {

    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

}