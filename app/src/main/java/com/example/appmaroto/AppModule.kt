package com.example.appmaroto

import com.example.appmaroto.ui.viewmodel.CadastroViewModel
import com.example.appmaroto.ui.viewmodel.GameDetailsViewModel
import com.example.appmaroto.ui.viewmodel.GamesListViewModel
import com.example.appmaroto.ui.viewmodel.LoginViewModel
import com.example.appmaroto.ui.viewmodel.MyAccountViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CadastroViewModel() }
    viewModel { LoginViewModel() }
    viewModel { GamesListViewModel() }
    viewModel { MyAccountViewModel() }
    viewModel { GameDetailsViewModel() }
}