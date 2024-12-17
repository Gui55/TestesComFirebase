package com.example.appmaroto.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmaroto.ResultStatus
import com.example.appmaroto.model.Game
import com.example.appmaroto.model.GameDoc
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

private const val GAMES_COLLECTION = "games"

object GameRepository {
    private val firestore by lazy { Firebase.firestore }

    fun addGame(game: Game): LiveData<ResultStatus<Boolean>> =
        MutableLiveData<ResultStatus<Boolean>>().apply {
            value = ResultStatus.Loading
            val doc = firestore.collection(GAMES_COLLECTION)
                .document()
            val gameDoc = GameDoc(game.nome, game.desenvolvedora, game.publisher)
            doc.set(gameDoc)
            value = ResultStatus.Success(true)
        }

    fun getGames(): LiveData<ResultStatus<List<Game>>> =
        MutableLiveData<ResultStatus<List<Game>>>().apply {
            value = ResultStatus.Loading
            firestore.collection(GAMES_COLLECTION)
                .addSnapshotListener { snapshot, error ->
                    snapshot?.let {
                        val games = mutableListOf<Game>()
                        for (document in it.documents) {
                            val game = document.toObject(Game::class.java)
                            game?.let { itGame ->
                                val gameWithId = itGame.copy(id = document.id)
                                games.add(gameWithId)
                            }
                        }
                        value = ResultStatus.Success(games)
                    }
                    error?.let {
                        value = ResultStatus.Error(it.message ?: "Erro ao buscar jogos")
                    }
                }
        }

    fun editGame(game: Game): LiveData<ResultStatus<Boolean>> =
        MutableLiveData<ResultStatus<Boolean>>().apply {
            value = ResultStatus.Loading
            val doc = firestore.collection(GAMES_COLLECTION)
                .document(game.id ?: "")
            val gameDoc = GameDoc(game.nome, game.desenvolvedora, game.publisher)
            doc.set(gameDoc)
            value = ResultStatus.Success(true)
        }

    fun deleteGame(gameId: String?): LiveData<ResultStatus<Boolean>> =
        MutableLiveData<ResultStatus<Boolean>>().apply {
            value = ResultStatus.Loading
            firestore.collection(GAMES_COLLECTION)
                .document(gameId ?: "")
                .delete()
            value = ResultStatus.Success(true)
        }

    fun getGameDetails(id: String): LiveData<ResultStatus<Game>> =
        MutableLiveData<ResultStatus<Game>>().apply {
            value = ResultStatus.Loading
            firestore.collection(GAMES_COLLECTION)
                .document(id)
                .addSnapshotListener{ snapshot, error ->
                    snapshot?.let{
                        val game = it.toObject(Game::class.java)
                        game?.let{
                            value = ResultStatus.Success(it)
                        }
                    }
                    error?.let{
                        value = ResultStatus.Error(it.message ?: "Erro ao buscar detalhes do jogo")
                    }
                }
        }
}