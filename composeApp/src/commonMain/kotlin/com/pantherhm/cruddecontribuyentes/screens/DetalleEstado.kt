package com.pantherhm.cruddecontribuyentes.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import viewModel.StateListViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.awt.Button
import  androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ListaMunicipios(nombreEstado: String, viewModel: StateListViewModel, navController: NavHostController) {
    val estado = viewModel.states.find { it.nombre == nombreEstado }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        var search by remember { mutableStateOf("") }
        val fs1 = (maxWidth.value * 0.05f).sp
        val fs2 = (maxWidth.value * 0.03f).sp
        val columns = when {
            maxWidth < 600.dp -> 1
            maxWidth < 800.dp -> 2
            else -> 3
        }

        Column(modifier = Modifier.fillMaxSize()) {
            if (estado == null || estado.municipios.isEmpty())
            {
                Text(
                    text = "No hay municipios aun",
                    modifier = Modifier
                        .fillMaxHeight(0.2f)
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = fs1
                    ))
                return@Column
            }
            TextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Buscar municipio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))

            val filtered = estado.municipios.filter {
                it.nombre.contains(search, ignoreCase = true)
            }

            if(filtered.isEmpty()){
                Text(
                    text = "No se encontraron resultados",
                    modifier = Modifier
                        .fillMaxHeight(0.2f)
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = fs1
                    ))
                return@Column
            }

            //Lista de estados 0 /// 0
            if (columns == 1) {
                LazyColumn {
                    items(filtered) { item ->
                        Button(
                            onClick = { navController.navigate("detallemunicipio/${item}") },
                            modifier = Modifier.padding(8.dp),
                            shape = RectangleShape
                        ) {
                            Text(
                                text = item.nombre,
                                modifier = Modifier
                                    .fillMaxHeight(0.2f)
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = fs1
                                )
                            )
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filtered) { item ->
                        Button(
                            onClick = { navController.navigate("detallemunicipio/${item}") },
                            modifier = Modifier.padding(8.dp),
                            shape = RectangleShape
                        ) {
                            Text(
                                text = item.nombre,
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontSize = fs2),
                                modifier = Modifier
                                    .fillMaxHeight(0.1f)
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

}