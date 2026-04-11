package viewModel

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList


data class Estado(val nombre: String, val municipios: List<Municipio>)
data class Municipio(val nombre : String, val contribuidores: List<String>)

class StateListViewModel {
    var states = mutableStateListOf(
        Estado("Guanajuato", listOf(Municipio("Uriangato", listOf()), Municipio("Moroleón", listOf()), Municipio("Irapuato", listOf()))),
        Estado("Jalisco", listOf(Municipio("Guadalajara", listOf()), Municipio("Zapopan", listOf()), Municipio("Tlapelalque", listOf()))),
        Estado("Hidalgo", listOf()),
        Estado("Colima", listOf()),
        Estado("San Luis Potosi", listOf()),
        Estado("Chihuahua", listOf()),
        Estado("Durango", listOf()),
        Estado("Aguascalientes", listOf()),
        Estado("Oaxaca", listOf()),
        Estado("Yucatan", listOf()),
        Estado("Veracruz", listOf()),
        Estado("Tlaxcala", listOf()),
        Estado("Estado de Mexico", listOf()),
        Estado("Nuevo Leon", listOf()),
        Estado("Guerrero", listOf()),
        Estado("Campeche", listOf()),
        Estado("Coahuila", listOf()),
        Estado("Morelos", listOf()),
        Estado("Nayarit", listOf()),
        Estado("Queretaro", listOf()),
        Estado("Quintana Roo", listOf()),
        Estado("Sonora", listOf()),
        Estado("Tabasco", listOf()),
        Estado("Tamaulipas", listOf()),
        Estado("Zacatecas", listOf()),
        Estado("Ciudad de Mexico", listOf()),
        Estado("Baja California", listOf()),
        Estado("Baja California Sur", listOf()),
        Estado("Chiapas", listOf()),
        Estado("Puebla", listOf()),
        Estado("Sinaloa", listOf())
        )
    init {
        states = states.sortedBy { it.nombre }.toMutableStateList()
    }

    fun RemoveEstado(nombre : String)
    {
        states.removeIf { it.nombre == nombre }
    }
}