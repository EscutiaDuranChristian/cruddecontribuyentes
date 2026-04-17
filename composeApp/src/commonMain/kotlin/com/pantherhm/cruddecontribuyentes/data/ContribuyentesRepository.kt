package com.pantherhm.cruddecontribuyentes.data

import app.cash.sqldelight.db.SqlDriver
import com.pantherhm.cruddecontribuyentes.db.ContribuyentesDatabase
import viewModel.DomicilioFiscal
import viewModel.Estado
import viewModel.Municipio
import viewModel.Persona
import viewModel.PersonaFisica
import viewModel.PersonaMoral

class ContribuyentesRepository(driver: SqlDriver) {
    private val database = ContribuyentesDatabase(driver)
    private val queries = database.contribuyenteQueries

    fun getAllStates(): List<Estado> {
        return queries.getAllEstados().executeAsList().map { estado ->
            val municipios = queries.getAllMunicipiosByEstado(estado.id).executeAsList()
                .map { municipio ->
                    Municipio(
                        id = municipio.id.toInt(),
                        nombre = municipio.nombre,
                        nab = municipio.nab,
                        contribuidores = getPersonasByMunicipioId(municipio.id).toMutableList()
                    )
                }
                .sortedBy { it.nombre }
                .toMutableList()

            Estado(
                id = estado.id.toInt(),
                nombre = estado.nombre,
                nab = estado.nab,
                municipios = municipios
            )
        }
    }

    fun seedStates(states: List<Estado>) {
        database.transaction {
            states.forEach { estado ->
                queries.insertEstado(estado.nombre, estado.nab)
            }
        }
    }

    fun insertEstado(nombre: String, nab: String) {
        queries.insertEstado(nombre, nab)
    }

    fun updateEstado(id: Int, nombre: String, nab: String) {
        queries.updateEstado(nombre, nab, id.toLong())
    }

    fun deleteEstado(id: Int) {
        database.transaction {
            queries.getAllMunicipiosByEstado(id.toLong()).executeAsList().forEach { municipio ->
                deleteMunicipioInternal(municipio.id)
            }
            queries.deleteEstado(id.toLong())
        }
    }

    fun insertMunicipio(estadoId: Int, nombre: String, nab: String) {
        queries.insertMunicipio(nombre, nab, estadoId.toLong())
    }

    fun updateMunicipio(id: Int, nombre: String, nab: String) {
        queries.updateMunicipio(nombre, nab, id.toLong())
    }

    fun deleteMunicipio(id: Int) {
        database.transaction {
            deleteMunicipioInternal(id.toLong())
        }
    }

    fun insertPersona(municipioId: Int, persona: Persona) {
        when (persona) {
            is PersonaFisica -> queries.insertPersonaFisica(
                curp = persona.curp,
                nombre_completo = persona.nombreCompleto,
                fecha_nac = persona.fechaNac,
                correo_elec = persona.correoElec,
                tel = persona.tel,
                municipio_id = municipioId.toLong(),
                cp = persona.domicilioFiscal.cp,
                estado = persona.domicilioFiscal.estado,
                municipio = persona.domicilioFiscal.municipio,
                localidad = persona.domicilioFiscal.localidad,
                colonia = persona.domicilioFiscal.colonia,
                vialidad = persona.domicilioFiscal.vialidad,
                nombre_vialidad = persona.domicilioFiscal.nombreVialidad,
                numero_exterior = persona.domicilioFiscal.numeroExterior.toLongValue(),
                numero_interior = persona.domicilioFiscal.numeroInterior.toLongValue(),
                entrecalle1 = persona.domicilioFiscal.entrecalle1,
                entrecalle2 = persona.domicilioFiscal.entrecalle2,
                referencia_adicional = persona.domicilioFiscal.referenciaAdicional,
                caracteristicas = persona.domicilioFiscal.caracteristicas,
                actividad_economica = persona.domicilioFiscal.actividadEconomica,
                regimen_fiscal = persona.domicilioFiscal.regimenFiscal
            )

            is PersonaMoral -> queries.insertPersonaMoral(
                razon_social = persona.razonSocial,
                fecha_const = persona.fechaConst,
                rfc_representante = persona.rfcRepresentante,
                rfc_socio = persona.rfcSocio,
                no_poliza = persona.noPoliza.toLong(),
                regimen_capital = persona.regimenCapital,
                municipio_id = municipioId.toLong(),
                cp = persona.domicilioFiscal.cp,
                estado = persona.domicilioFiscal.estado,
                municipio = persona.domicilioFiscal.municipio,
                localidad = persona.domicilioFiscal.localidad,
                colonia = persona.domicilioFiscal.colonia,
                vialidad = persona.domicilioFiscal.vialidad,
                nombre_vialidad = persona.domicilioFiscal.nombreVialidad,
                numero_exterior = persona.domicilioFiscal.numeroExterior.toLongValue(),
                numero_interior = persona.domicilioFiscal.numeroInterior.toLongValue(),
                entrecalle1 = persona.domicilioFiscal.entrecalle1,
                entrecalle2 = persona.domicilioFiscal.entrecalle2,
                referencia_adicional = persona.domicilioFiscal.referenciaAdicional,
                caracteristicas = persona.domicilioFiscal.caracteristicas,
                actividad_economica = persona.domicilioFiscal.actividadEconomica,
                regimen_fiscal = persona.domicilioFiscal.regimenFiscal
            )
        }
    }

    fun updatePersona(municipioId: Int, tipo: String, original: String, persona: Persona) {
        when {
            tipo == "fisica" && persona is PersonaFisica -> {
                val originalPersona = queries.getAllPersonasFisicasByMunicipio(municipioId.toLong())
                    .executeAsList()
                    .firstOrNull { it.curp.equals(original, ignoreCase = true) }
                    ?: return

                queries.updatePersonaFisica(
                    curp = persona.curp,
                    nombre_completo = persona.nombreCompleto,
                    fecha_nac = persona.fechaNac,
                    correo_elec = persona.correoElec,
                    tel = persona.tel,
                    cp = persona.domicilioFiscal.cp,
                    estado = persona.domicilioFiscal.estado,
                    municipio = persona.domicilioFiscal.municipio,
                    localidad = persona.domicilioFiscal.localidad,
                    colonia = persona.domicilioFiscal.colonia,
                    vialidad = persona.domicilioFiscal.vialidad,
                    nombre_vialidad = persona.domicilioFiscal.nombreVialidad,
                    numero_exterior = persona.domicilioFiscal.numeroExterior.toLongValue(),
                    numero_interior = persona.domicilioFiscal.numeroInterior.toLongValue(),
                    entrecalle1 = persona.domicilioFiscal.entrecalle1,
                    entrecalle2 = persona.domicilioFiscal.entrecalle2,
                    referencia_adicional = persona.domicilioFiscal.referenciaAdicional,
                    caracteristicas = persona.domicilioFiscal.caracteristicas,
                    actividad_economica = persona.domicilioFiscal.actividadEconomica,
                    regimen_fiscal = persona.domicilioFiscal.regimenFiscal,
                    id = originalPersona.id
                )
            }

            tipo == "moral" && persona is PersonaMoral -> {
                val originalPersona = queries.getAllPersonasMoralesByMunicipio(municipioId.toLong())
                    .executeAsList()
                    .firstOrNull { it.rfc_representante.equals(original, ignoreCase = true) }
                    ?: return

                queries.updatePersonaMoral(
                    razon_social = persona.razonSocial,
                    fecha_const = persona.fechaConst,
                    rfc_representante = persona.rfcRepresentante,
                    rfc_socio = persona.rfcSocio,
                    no_poliza = persona.noPoliza.toLong(),
                    regimen_capital = persona.regimenCapital,
                    cp = persona.domicilioFiscal.cp,
                    estado = persona.domicilioFiscal.estado,
                    municipio = persona.domicilioFiscal.municipio,
                    localidad = persona.domicilioFiscal.localidad,
                    colonia = persona.domicilioFiscal.colonia,
                    vialidad = persona.domicilioFiscal.vialidad,
                    nombre_vialidad = persona.domicilioFiscal.nombreVialidad,
                    numero_exterior = persona.domicilioFiscal.numeroExterior.toLongValue(),
                    numero_interior = persona.domicilioFiscal.numeroInterior.toLongValue(),
                    entrecalle1 = persona.domicilioFiscal.entrecalle1,
                    entrecalle2 = persona.domicilioFiscal.entrecalle2,
                    referencia_adicional = persona.domicilioFiscal.referenciaAdicional,
                    caracteristicas = persona.domicilioFiscal.caracteristicas,
                    actividad_economica = persona.domicilioFiscal.actividadEconomica,
                    regimen_fiscal = persona.domicilioFiscal.regimenFiscal,
                    id = originalPersona.id
                )
            }
        }
    }

    fun deletePersona(municipioId: Int, tipo: String, identify: String) {
        when (tipo) {
            "fisica" -> queries.getAllPersonasFisicasByMunicipio(municipioId.toLong())
                .executeAsList()
                .firstOrNull { it.curp.equals(identify, ignoreCase = true) }
                ?.let { queries.deletePersonaFisica(it.id) }

            "moral" -> queries.getAllPersonasMoralesByMunicipio(municipioId.toLong())
                .executeAsList()
                .firstOrNull { it.rfc_representante.equals(identify, ignoreCase = true) }
                ?.let { queries.deletePersonaMoral(it.id) }
        }
    }

    private fun getPersonasByMunicipioId(municipioId: Long): List<Persona> {
        val personasFisicas = queries.getAllPersonasFisicasByMunicipio(municipioId).executeAsList().map { persona ->
            PersonaFisica(
                domicilioFiscal = persona.toDomicilioFiscal(),
                curp = persona.curp,
                nombreCompleto = persona.nombre_completo,
                fechaNac = persona.fecha_nac,
                correoElec = persona.correo_elec,
                tel = persona.tel
            )
        }

        val personasMorales = queries.getAllPersonasMoralesByMunicipio(municipioId).executeAsList().map { persona ->
            PersonaMoral(
                domicilioFiscal = persona.toDomicilioFiscal(),
                razonSocial = persona.razon_social,
                fechaConst = persona.fecha_const,
                rfcRepresentante = persona.rfc_representante,
                rfcSocio = persona.rfc_socio,
                noPoliza = persona.no_poliza.toInt(),
                regimenCapital = persona.regimen_capital
            )
        }

        return personasFisicas + personasMorales
    }

    private fun deleteMunicipioInternal(id: Long) {
        queries.getAllPersonasFisicasByMunicipio(id).executeAsList().forEach { persona ->
            queries.deletePersonaFisica(persona.id)
        }
        queries.getAllPersonasMoralesByMunicipio(id).executeAsList().forEach { persona ->
            queries.deletePersonaMoral(persona.id)
        }
        queries.deleteMunicipio(id)
    }
}

private fun com.pantherhm.cruddecontribuyentes.db.PersonaFisica.toDomicilioFiscal(): DomicilioFiscal {
    return DomicilioFiscal(
        cp = cp,
        estado = estado,
        municipio = municipio,
        localidad = localidad,
        colonia = colonia,
        vialidad = vialidad,
        nombreVialidad = nombre_vialidad,
        numeroExterior = numero_exterior.toString(),
        numeroInterior = numero_interior.toString(),
        entrecalle1 = entrecalle1,
        entrecalle2 = entrecalle2,
        referenciaAdicional = referencia_adicional,
        caracteristicas = caracteristicas,
        actividadEconomica = actividad_economica,
        regimenFiscal = regimen_fiscal
    )
}

private fun com.pantherhm.cruddecontribuyentes.db.PersonaMoral.toDomicilioFiscal(): DomicilioFiscal {
    return DomicilioFiscal(
        cp = cp,
        estado = estado,
        municipio = municipio,
        localidad = localidad,
        colonia = colonia,
        vialidad = vialidad,
        nombreVialidad = nombre_vialidad,
        numeroExterior = numero_exterior.toString(),
        numeroInterior = numero_interior.toString(),
        entrecalle1 = entrecalle1,
        entrecalle2 = entrecalle2,
        referenciaAdicional = referencia_adicional,
        caracteristicas = caracteristicas,
        actividadEconomica = actividad_economica,
        regimenFiscal = regimen_fiscal
    )
}

private fun String.toLongValue(): Long = toLongOrNull() ?: 0L
