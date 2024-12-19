package com.example.appmaroto.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmaroto.ResultStatus
import com.example.appmaroto.ui.viewmodel.GamesListViewModel
import com.example.appmaroto.databinding.ActivityGamesListBinding
import com.example.appmaroto.databinding.LayoutAddGameBinding
import com.example.appmaroto.googleSignInClient
import com.example.appmaroto.model.Game
import com.example.appmaroto.snackbar
import com.example.appmaroto.ui.recycler.GamesAdapter
import com.example.appmaroto.ui.recycler.OnGameClickListener
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

class GamesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamesListBinding
    private val viewModel: GamesListViewModel by viewModel()
    private lateinit var rootView: View
    private lateinit var adapter: GamesAdapter
    private lateinit var addGameDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rootView = findViewById(android.R.id.content)

        setupDialog()
        setupRecyclerView()
        setupButtonClicks()
        requestGames()
    }

    private fun setupDialog(){
        val dialogBinding = LayoutAddGameBinding.inflate(LayoutInflater.from(this))
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)

        addGameDialog = dialogBuilder.create()

        dialogBinding.btnAddGame.setOnClickListener{
            val game = Game(
                null,
                dialogBinding.etGameName.text.toString(),
                dialogBinding.etDeveloper.text.toString(),
                dialogBinding.etPublisher.text.toString()
            )
            addGame(game)
        }
    }

    private fun addGame(game: Game){
        viewModel.addGame(game).observe(this){ status ->
            when(status){
                is ResultStatus.Success -> {
                    rootView.snackbar("Jogo adicionado com sucesso")
                    addGameDialog.dismiss()
                }
                is ResultStatus.Error -> {
                    rootView.snackbar(status.errorMessage)
                    addGameDialog.dismiss()
                }
                is ResultStatus.Loading -> {
                }
            }
        }
    }

    private fun setupRecyclerView(){
        adapter = GamesAdapter(object: OnGameClickListener{
            override fun onGameClick(gameId: String) {
                val intent = Intent(this@GamesListActivity, GameDetailsActivity::class.java)
                intent.putExtra("gameId", gameId)
                startActivity(intent)
            }
        })
        binding.rvGames.adapter = adapter
        binding.rvGames.layoutManager = LinearLayoutManager(this)
    }

    private fun setupButtonClicks(){
        binding.buttonLogout.setOnClickListener{
            viewModel.logout()
            this.googleSignInClient().signOut()
            thread{
                FirebaseMessaging.getInstance().deleteToken()
            }
            finish()
        }

        binding.buttonMyAccount.setOnClickListener{
            startActivity(Intent(this, MyAccountActivity::class.java))
        }

        binding.buttonAddGame.setOnClickListener{
            addGameDialog.show()
        }

        binding.buttonSubscribe.setOnClickListener{
            viewModel.subscribeMarotagens()
        }

        binding.buttonUnsubscribe.setOnClickListener{
            viewModel.unsubscribeMarotagens()
        }
    }

    private fun requestGames(){
        viewModel.getGames().observe(this){ status ->
            when(status){
                is ResultStatus.Success -> {
                    adapter.submitList(status.data)
                }
                is ResultStatus.Error -> {
                    rootView.snackbar(status.errorMessage)
                }
                is ResultStatus.Loading -> {
                }
            }
        }
    }
}