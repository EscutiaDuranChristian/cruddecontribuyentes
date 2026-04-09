package com.pantherhm.cruddecontribuyentes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform