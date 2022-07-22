package com.example.countriesapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RestCountriesApi {
    @GET("name/{name}/")
    suspend fun getCountryByName(@Path("name") cityName: String): List<Country>
    // Хз пока как работает, но "за счет анотации @Path переменная "name" передается в @GET("v3.1/name/{name}")

    /*
    Тк запрос выполняется не моментально, то нужно поставить какой-то таймаут для функции
    Первый вариант:
        fun getCountryByName(@Path("name") cityName: String): Call<List<Country>> - это непосредственно из руководства retrofit
        Тогда в MainActivity добавится .execute().body()!! - это типа Синхронный запрос.
        Но это хреновый вариант, потому что тогда программа будет ждать исполнения данного кода, а интерфейс зависнет

    Второй вариант:
        Запустить данную функцию Асинхронно в режиме многопоточности. С использованием карутин (это типа легковесные потоки и особенность языка котлин)
        1. suspend fun getCountryByName(@Path("name") cityName: String): List<Country> помечается модификатором suspend
        2. Дальше в MainActivity функция подсветится красным, говоря тем самым, что
            "suspend функция может быть запущена только из другой suspend функции или из корутины"
        3. Дальше создает карутину lifecycleScope.launch {} и помещаем весь нужный код туда
     */
}

// Так-с, вот эту шнягу я вообще пока не понимаю. Ну, вернее что откуда и для чего.
var retrofit = Retrofit.Builder()
    .baseUrl("https://restcountries.com/v3.1/")  // Это типа для обработки (подключения) restApi для указанного baseUrl
    .addConverterFactory(GsonConverterFactory.create())
    // Так-с, что заметил: все импорты должны идти до .build(). Видимо, он потом это как-то обрабатывает.
    .build()


var restCountriesApi = retrofit.create(RestCountriesApi::class.java)