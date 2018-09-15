package br.com.livroandroid.carros.domain

import br.com.livroandroid.carros.extensions.fromJson
import br.com.livroandroid.carros.utils.HttpHelper

object CarroService {
    private const val BASE_URL
            = "http://livrowebservices.com.br/rest/carros"

    fun getCarros(tipo: TipoCarro): List<Carro> {


        val url = "$BASE_URL/tipo/${tipo.name.toLowerCase()}"

        val json = HttpHelper.get(url)

        return fromJson(json)
    }
}