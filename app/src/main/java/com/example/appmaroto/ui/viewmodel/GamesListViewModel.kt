package com.example.appmaroto.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmaroto.model.Game
import com.example.appmaroto.repository.GameRepository
import com.example.appmaroto.repository.UserRepository

class GamesListViewModel: ViewModel() {

    fun addGame(game: Game) = GameRepository.addGame(game)

    fun getGames() = GameRepository.getGames()

    fun logout() = UserRepository.logout()

}