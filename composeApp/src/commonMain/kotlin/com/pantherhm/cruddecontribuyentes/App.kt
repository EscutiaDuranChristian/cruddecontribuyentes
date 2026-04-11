package com.pantherhm.cruddecontribuyentes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pantherhm.cruddecontribuyentes.screens.ListaMunicipios
import com.pantherhm.cruddecontribuyentes.screens.StateListScreen
import androidx.navigation.compose.rememberNavController
import viewModel.StateListViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Alignment


@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}

@Composable
fun AppNavHost(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize())
    {

        val viewmodel = remember { StateListViewModel() }

        NavHost(navController, startDestination = "listaEstados") {
            composable("listaEstados") {
                StateListScreen(navController, viewmodel)
            }
            composable("detalleestado/{nombreEstado}") { backStackEntry ->
                val nombreEstado = backStackEntry.savedStateHandle.get<String>("nombreEstado") ?: ""
                ListaMunicipios(nombreEstado, viewmodel, navController)
            }
        }

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        if (currentRoute != "listaEstados") {
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
                onClick = { navController.popBackStack() }
            ) {
                Text("Volver")
            }
        }
    }
}