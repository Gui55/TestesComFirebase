package com.example.appmaroto.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmaroto.model.Game
import com.example.appmaroto.repository.GameRepository

class GameDetailsViewModel: ViewModel() {
    fun getGameDetails(id: String) = GameRepository.getGameDetails(id)

    fun editGame(game: Game) = GameRepository.editGame(game)

    fun deleteGame(gameId: String?) = GameRepository.deleteGame(gameId)
}