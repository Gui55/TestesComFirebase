package com.example.appmaroto.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.appmaroto.ui.viewmodel.CadastroViewModel
import com.example.appmaroto.ResultStatus
import com.example.appmaroto.model.User
import com.example.appmaroto.databinding.ActivityCadastroBinding
import com.example.appmaroto.snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private val viewModel: CadastroViewModel by viewModel()

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rootView = findViewById(android.R.id.content)

        binding.btnCadastrar.setOnClickListener {
            registerBtnAction()
        }
    }

    private fun registerBtnAction(){
        resetValidations()

        val emailText = binding.editEmail.text.toString()
        val passwordText = binding.editSenha.text.toString()

        if(validate(emailText, passwordText)){
            requestCreateUser(User(emailText, passwordText))
        }

    }

    private fun requestCreateUser(user: User) {
        viewModel.cadastrarUsuario(user).observe(this) { status ->
            when (status) {
                is ResultStatus.Success -> {
                    rootView.snackbar("Cadastro realizado com sucesso")
                }

                is ResultStatus.Error -> {
                    rootView.snackbar(status.errorMessage)
                }

                is ResultStatus.Loading -> {
                }
            }
        }
    }

    private fun validate(
        emailText: String,
        passwordText: String
    ): Boolean {
        var valid = true
        if (emailText.isEmpty()) {
            binding.editEmail.error = "É necessário um email"
            valid = false
        }

        if (passwordText.isEmpty()) {
            binding.editSenha.error = "É necessário uma senha"
            valid = false
        }
        return valid
    }

    private fun resetValidations() {
        binding.editEmail.error = null
        binding.editSenha.error = null
    }
}