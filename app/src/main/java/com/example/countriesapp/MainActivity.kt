package com.example.countriesapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.countriesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

        // Обработчик нажатия на кнопку
        binding.searchButton.setOnClickListener {
            // Получаю соответственно данные из нужного мне поля countryNameEditText
            val countryName = binding.countryNameEditText.text.toString()

            // Так-с, замечу, что я не импортирую restCountriesApi, который лежит в RemoteDataSource
            // Если зажать ctrl и начать на restCountriesApi, то он перейдет туда, где эта хрень лежит, т.е. в RemoteDataSource
            // Также отработает и для countryName
            // lifecycleScope.launch - эта хуйня называется карутиной. Изи каточка! Типа создан второстепенный поток russia
            lifecycleScope.launch {
                try {
                    val countries = restCountriesApi.getCountryByName(countryName) // Сюда приходит инфа как в классе, что описали в Country
                    val country = countries[0] // Тк инфа выше приходит списком (это именно для этого api), то обращаемся к 0 austria

                    // а дальше обращаемся к country как классу. Вроде примерно так все это работает
                    binding.countryNameTextView.text = country.name.common
                    binding.capitalTextView.text = country.capital[0]
                    binding.populationTextView.text = formatNumber(country.population) // Опять же замечу, функцию не импортирую
                    binding.areaTextView.text = formatNumber(country.area.toLong())
                    binding.languagesTextView.text = country.languages.toString().substringAfter("=").dropLast(1).split(' ').joinToString()

                    loadSvg(binding.imageView, country.flags.png)

                    // А вот и обращение к атрибутам напрямую
                    binding.resultLayout.visibility = View.VISIBLE
                    binding.statusLayout.visibility = View.INVISIBLE
                }catch (e: Exception){
                    binding.statusTextView.text = "Ошибочка! Такой страны не существует!"
                    binding.imageStatus.setImageResource(R.drawable.ic_baseline_error_outline_24)
                    binding.resultLayout.visibility = View.INVISIBLE
                    binding.statusLayout.visibility = View.VISIBLE
                }

            }
        }
    }
}



