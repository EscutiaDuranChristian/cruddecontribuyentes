package com.pantherhm.cruddecontribuyentes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import viewModel.StateListViewModel
import  androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.pantherhm.cruddecontribuyentes.Dialogs.DeleteWarning
import viewModel.Estado

@Composable
fun StateListScreen(navController: NavController, viewModel: StateListViewModel) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        var search by remember { mutableStateOf("") }
        val fs1 = (maxWidth.value * 0.05f).sp
        val fs2 = (maxWidth.value * 0.03f).sp
        val columns = when {
            maxWidth < 600.dp -> 1
            maxWidth < 800.dp -> 2
            else -> 3
        }
        var showDialog by remember { mutableStateOf(false) }
        var estado by remember { mutableStateOf(Estado("", listOf())) }

        Column(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp).border(5.dp, Color.Black, RectangleShape)) {
            TextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Buscar estado") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))

            val filteredStates = viewModel.states.filter {
                it.nombre.contains(search, ignoreCase = true)
            }

            //Mensaje de resultados
            if(filteredStates.isEmpty()){
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
                    items(filteredStates) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth() // solo ancho, no alto completo
                                .padding(8.dp)
                        ) {
                            Button(
                                onClick = { navController.navigate("detalleestado/${item.nombre}") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RectangleShape
                            ) {
                                Text(
                                    text = item.nombre,
                                    modifier = Modifier
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(fontSize = fs1)
                                )
                            }

                            Button(
                                onClick = { showDialog = true; estado = item },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd) // lo pegas a la derecha
                                    .padding(end = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("-")
                            }
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
                    items(filteredStates) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth() // solo ancho, no alto completo
                                .padding(8.dp)
                        ) {
                            Button(
                                onClick = { navController.navigate("detalleestado/${item.nombre}") },
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

                            Button(
                                onClick = { showDialog = true; estado = item },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd) // lo pegas a la derecha
                                    .padding(end = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("-")
                            }
                        }

                    }
                }
            }
        }
        Button(onClick = { navController.navigate("addestado") } ,
                colors = ButtonDefaults.buttonColors(Color.Green, Color.White),
                modifier = Modifier.align(Alignment.BottomEnd))
        {
            Text("Add")
        }

        if (showDialog) {
            DeleteWarning(
                nombre = estado.nombre,
                onConfirm = {
                    viewModel.RemoveEstado(estado.nombre)
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}