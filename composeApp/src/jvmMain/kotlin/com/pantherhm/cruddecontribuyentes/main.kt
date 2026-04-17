package com.pantherhm.cruddecontribuyentes

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.pantherhm.cruddecontribuyentes.data.ContribuyentesRepository
import com.pantherhm.cruddecontribuyentes.db.ContribuyentesDatabase
import java.io.File
import viewModel.StateListViewModel

fun main() = application {
    val databaseFile = File("contribuyentes.db")
    val driver = JdbcSqliteDriver("jdbc:sqlite:${databaseFile.absolutePath}").also { driver ->
        if (!databaseFile.exists()) {
            ContribuyentesDatabase.Schema.create(driver)
        }
    }
    val viewModel = StateListViewModel(ContribuyentesRepository(driver))

    Window(
        onCloseRequest = ::exitApplication,
        title = "cruddecontribuyentes",
    ) {
        App(viewModel)
    }
}
