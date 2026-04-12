package viewModel

enum class AnsStates
{
    Acepted,
    Repeted,
    BadFormat
}

fun AnsStates.Message() : String = when (this)
{
    AnsStates.Acepted -> "Operacion exitosamente completada!"
    AnsStates.Repeted -> "El valor no fue aceptado, porque ya se encuentra en la base de datos"
    AnsStates.BadFormat -> "El formato es incorrecto. Procura escribir mayusculas y minusculas de forma correcta. Ademas cuida tus espacios." +
                          "\nEjemplos:\nPaseo El Alto\nMariano De La Cruz\nFráncisco Vega\nMaría Ñuñez Del Valle"
}