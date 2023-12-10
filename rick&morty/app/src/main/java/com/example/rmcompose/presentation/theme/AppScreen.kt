package com.example.rmcompose.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.example.rmcompose.data.model.Result
import com.example.rmcompose.presentation.MainViewModel

enum class AppScreens() {
    ListPersonScreen,
    DescribesScreen

}

@Composable
fun AppScreen(
    persons: LazyPagingItems<Result>,
    viewModel: MainViewModel,
    navController: NavHostController = rememberNavController()
) {
    val episodes = viewModel.episodesList.collectAsState()
    NavHost(navController = navController, startDestination = AppScreens.ListPersonScreen.name) {
        composable(AppScreens.ListPersonScreen.name) {
            MainScreenList(persons = persons, viewModel = viewModel, navController = navController)
        }
        composable(AppScreens.DescribesScreen.name) {
            DescribePersonScreen(viewModel.person, episodes.value)
        }

    }


}