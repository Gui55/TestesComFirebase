package com.example.appmaroto.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmaroto.R
import com.example.appmaroto.ResultStatus
import com.example.appmaroto.databinding.ActivityGameDetailsBinding
import com.example.appmaroto.databinding.LayoutEditGameBinding
import com.example.appmaroto.model.Game
import com.example.appmaroto.snackbar
import com.example.appmaroto.ui.viewmodel.GameDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameDetailsBinding

    private val viewModel: GameDetailsViewModel by viewModel()

    private lateinit var rootView: View

    private var gameId: String? = ""

    private lateinit var editGameDialog: AlertDialog
    private lateinit var dialogBinding: LayoutEditGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rootView = findViewById(android.R.id.content)

        gameId = intent.getStringExtra("gameId")
        setupDialog()
        setupEditGameBtn()
        getSpecificGame()
        setupGameDeletion()
    }

    private fun setupDialog(){
        dialogBinding = LayoutEditGameBinding.inflate(LayoutInflater.from(this))
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogBinding.root)

        editGameDialog = dialogBuilder.create()

        dialogBinding.btnEditGame.setOnClickListener{
            val game = gameObjectCreation(dialogBinding)
            requestGameEdit(game)
            editGameDialog.dismiss()
        }
    }

    private fun gameObjectCreation(dialogBinding: LayoutEditGameBinding) = Game(
        gameId,
        dialogBinding.etGameName.text.toString(),
        dialogBinding.etDeveloper.text.toString(),
        dialogBinding.etPublisher.text.toString()
    )

    private fun requestGameEdit(game: Game){
        viewModel.editGame(game).observe(this) { status ->
            when(status){
                is ResultStatus.Success -> {
                    rootView.snackbar("Jogo editado com sucesso")
                }
                is ResultStatus.Error -> {
                    rootView.snackbar(status.errorMessage)
                }
                is ResultStatus.Loading -> {

                }
            }
        }

    }

    private fun setupEditGameBtn(){
        binding.btEdit.setOnClickListener{
            dialogBinding.etGameName.setText(binding.tvGameName.text)
            dialogBinding.etDeveloper.setText(binding.tvDeveloper.text)
            dialogBinding.etPublisher.setText(binding.tvPublisher.text)
            editGameDialog.show()
        }
    }

    private fun getSpecificGame() {
        gameId?.let { gameId ->
            viewModel.getGameDetails(gameId).observe(this) { status ->
                when (status) {
                    is ResultStatus.Success -> {
                        binding.tvGameName.text = status.data.nome
                        binding.tvDeveloper.text = status.data.desenvolvedora
                        binding.tvPublisher.text = status.data.publisher
                    }

                    is ResultStatus.Error -> {
                        rootView.snackbar("Erro ao consultar jogo")
                    }

                    is ResultStatus.Loading -> {

                    }
                }
            }
        }
    }

    private fun setupGameDeletion(){
        binding.btDelete.setOnClickListener{
            requestGameDeletion()
        }
    }

    private fun requestGameDeletion(){
        viewModel.deleteGame(gameId).observe(this) { status ->
            when(status){
                is ResultStatus.Success -> {
                    rootView.snackbar("Jogo deletado com sucesso")
                    finish()
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