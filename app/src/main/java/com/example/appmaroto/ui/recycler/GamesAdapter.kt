package com.example.appmaroto.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.appmaroto.databinding.LayoutGameItemBinding
import com.example.appmaroto.model.Game

class GamesAdapter(private val onGameClickListener: OnGameClickListener): ListAdapter<Game, GameViewHolder>(GameDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutGameItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onGameClickListener
        )
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentGame = getItem(position)
        holder.bind(currentGame)
    }

}