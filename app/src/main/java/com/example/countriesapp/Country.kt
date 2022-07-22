package com.example.countriesapp

import java.util.*

// Даннай класс соответствует структуре json
open class Country(
    val name: Name, // Это было пиздец как странно
    val capital: List<String>,
    val population: Long,
    val area: Double,
    val languages: Any,
    val flags: Flag
)



/*
Данный класс создается, потому что в классе Country, в поле languages подается список из json такого формата:
languages: [
            {
                iso639_1: "en",
                iso639_2: "eng",
                name: "English"
                nativeName: "English"
            }
           ]
Отсюда на данный момент нужен всего лишь name.
 */

// Вот так, БЛЯТЬ, обрабатываюся ебучие объекты!
data class Name(
    val common: String
)

data class Flag(
    val png: String
)
