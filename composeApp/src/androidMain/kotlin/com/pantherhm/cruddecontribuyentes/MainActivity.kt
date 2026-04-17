package com.pantherhm.cruddecontribuyentes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pantherhm.cruddecontribuyentes.data.ContribuyentesRepository
import com.pantherhm.cruddecontribuyentes.db.ContribuyentesDatabase
import viewModel.StateListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val driver = remember {
                AndroidSqliteDriver(ContribuyentesDatabase.Schema, this, "contribuyentes.db")
            }
            val viewModel = remember { StateListViewModel(ContribuyentesRepository(driver)) }
            App(viewModel)
        }
    }
}
