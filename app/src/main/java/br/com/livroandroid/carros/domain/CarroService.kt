package br.com.livroandroid.carros.domain

import android.os.Build.VERSION_CODES.P
import android.util.Log
import br.com.livroandroid.carros.R.string.carros
import br.com.livroandroid.carros.extensions.fromJson
import br.com.livroandroid.carros.extensions.toJson
import br.com.livroandroid.carros.utils.HttpHelper
import br.com.livroandroid.carros.utils.Prefs

object CarroService {

    private const val TAG = "carros"

    private const val BASE_URL
            = "http://livrowebservices.com.br/rest/carros"

    fun getCarros(tipo: TipoCarro, refresh: Boolean = false): List<Carro> {
        val key = "json_{${tipo.name}"

        val cacheOn = Prefs.isCacheOn()

        if(cacheOn && !refresh) {
            val json = Prefs.getString(key)
            if(! json.isEmpty()) {
                // Existe no banco de dados
                return fromJson(json)
            }
        }

        // Busca no web service
        val url = "$BASE_URL/tipo/${tipo.name.toLowerCase()}"

        val json = HttpHelper.get(url)

        Prefs.putString(key, json)

        return fromJson(json)
    }

    fun save(carro: Carro) {

        val json = carro.toJson()

        val response = HttpHelper.post(BASE_URL, json)

        Log.d(TAG,response)
    }
}