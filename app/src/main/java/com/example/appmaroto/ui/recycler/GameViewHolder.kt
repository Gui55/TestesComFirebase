package com.example.appmaroto.ui.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.appmaroto.databinding.LayoutGameItemBinding
import com.example.appmaroto.model.Game

class GameViewHolder(
    private val binding: LayoutGameItemBinding,
    private val onGameClickListener: OnGameClickListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(game: Game){
        binding.tvGameName.text = game.nome
        binding.tvDeveloper.text = game.desenvolvedora
        binding.tvPublisher.text = game.publisher

        binding.root.setOnClickListener{
            game.id?.let {
                onGameClickListener.onGameClick(it)
            }
        }
    }
}

interface OnGameClickListener{
    fun onGameClick(gameId: String)
}
