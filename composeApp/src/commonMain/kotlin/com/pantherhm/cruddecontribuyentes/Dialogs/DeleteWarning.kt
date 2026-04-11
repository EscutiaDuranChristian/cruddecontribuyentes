package com.pantherhm.cruddecontribuyentes.Dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*

@Composable
fun DeleteWarning(
    nombre: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Confirmar eliminación") },
        text = { Text("¿Estás seguro de eliminar el elemento $nombre?") },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text("Sí, eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}