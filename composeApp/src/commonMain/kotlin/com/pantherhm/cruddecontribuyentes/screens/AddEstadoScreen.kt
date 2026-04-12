package com.pantherhm.cruddecontribuyentes.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import viewModel.AnsStates
import viewModel.Message
import viewModel.StateListViewModel

@Composable
fun AddEstadoScreen(viewModel: StateListViewModel) {
    var name by remember { mutableStateOf("") }
    var added by remember { mutableStateOf(AnsStates.Acepted) }
    var attemped by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxHeight(0.5f)
                .fillMaxWidth(0.8f)
                .align(Alignment.Center)
                .border(5.dp, Color.Black, RectangleShape),
            verticalArrangement = Arrangement.SpaceBetween, // distribuye arriba y abajo
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = name,
                onValueChange = { newValue ->
                    val filtered = newValue.replace(Regex("[^A-Za-zÁÉÍÓÚáéíóúÑñ ]"), "")
                    name = filtered
                },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f)
                    .padding(vertical = 20.dp),
            )

            if (attemped) {
                Text(
                    text = added.Message(),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                )
            }

            Button(
                onClick = {
                    added = viewModel.AddEstado(name)
                    attemped = true
                },
                modifier = Modifier.fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp),
            ) {  Text("Add")  }
        }
    }
}