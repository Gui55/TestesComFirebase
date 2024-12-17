package com.example.appmaroto.ui.activity

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.appmaroto.model.Game
import com.example.appmaroto.ResultStatus
import com.example.appmaroto.databinding.ActivityMainBinding
import com.example.appmaroto.googleSignInClient
import com.example.appmaroto.snackbar
import com.example.appmaroto.ui.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginViewModel by viewModel()

    private lateinit var rootView: View

    //val authUi = AuthUI.getInstance()

    private lateinit var idToken: String

    val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rootView = findViewById(android.R.id.content)

        binding.sibGoogleLogin.setSize(SignInButton.SIZE_STANDARD)
        val client = this.googleSignInClient()
        binding.sibGoogleLogin.setOnClickListener {
            startActivityForResult(client.signInIntent, RC_SIGN_IN)
        }


//            .addOnSuccessListener {
//                Log.i("GAAAMES", "Sucesso")
//            }

//        val intent = authUi.createSignInIntentBuilder()
//            .setAvailableProviders(
//                listOf(
//                    AuthUI.IdpConfig.EmailBuilder().build()
//                )
//            )
//            .setAlwaysShowSignInMethodScreen(true)
//            .build()
//        startActivityForResult(intent, RC_SIGN_IN)

        if(viewModel.isAnUserLoggedIn()){
            startActivity(Intent(this, GamesListActivity::class.java))
        }

        binding.buttonLogin.setOnClickListener{
            loginAction()
        }

        binding.buttonRegister.setOnClickListener{
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }

    private fun loginAction() {
        resetValidations()

        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (validate(email, password)) {
            requestLogin(email, password)
        }
    }

    private fun requestLogin(email: String, password: String) {
        viewModel.loginSuccess(email, password).observe(this) {
            when(it){
                is ResultStatus.Success -> {
                    startActivity(Intent(this, GamesListActivity::class.java))
                }
                is ResultStatus.Error -> {
                    rootView.snackbar(it.errorMessage)
                }
                is ResultStatus.Loading -> {
                }
            }
        }
    }

    private fun resetValidations() {
        binding.editTextEmail.error = null
        binding.editTextPassword.error = null
    }

    private fun validate(
        emailText: String,
        passwordText: String
    ): Boolean {
        var valid = true
        if (emailText.isEmpty()) {
            binding.editTextEmail.error = "É necessário um email"
            valid = false
        }

        if (passwordText.isEmpty()) {
            binding.editTextPassword.error = "É necessário uma senha"
            valid = false
        }
        return valid
    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?,
//        caller: ComponentCaller
//    ) {
//        super.onActivityResult(requestCode, resultCode, data, caller)
//        if(requestCode==RC_SIGN_IN){
//            if(resultCode==RESULT_OK){
//                startActivity(Intent(this, GamesListActivity::class.java))
//            }
//        } else {
//            val response = IdpResponse.fromResultIntent(data)
//            Log.e("FIREBASEUIERROR", response?.error?.errorCode.toString())
//            rootView.snackbar("Erro ao autenticar")
//        }
//    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        if (resultCode== RESULT_OK && requestCode == RC_SIGN_IN){
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account.idToken?.let { token ->
                idToken = token
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                viewModel.googleSignIn(credential).observe(this){ status ->
                    when(status){
                        is ResultStatus.Success -> {
                            startActivity(Intent(this, GamesListActivity::class.java))
                        }
                        is ResultStatus.Error -> {
                            rootView.snackbar(status.errorMessage)
                        }
                        is ResultStatus.Loading -> {
                        }
                    }
                }
            }
        } else {
            //val response = IdpResponse.fromResultIntent(data)
            //Log.e("FIREBASEUIERROR", response?.error?.errorCode.toString())
            rootView.snackbar("Erro")
        }
    }
}