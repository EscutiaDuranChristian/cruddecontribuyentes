package com.pantherhm.cruddecontribuyentes.Dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*

@Composable
fun AlterWarning(
    nombre: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Confirmar alteración") },
        text = { Text("¿Estás seguro de alterar este elemento: $nombre?") },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text("Sí, alterar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}