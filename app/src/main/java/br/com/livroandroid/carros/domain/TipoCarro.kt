package br.com.livroandroid.carros.domain

import androidx.annotation.StringRes
import br.com.livroandroid.carros.R

enum class TipoCarro(@StringRes val abacaxi: Int) {
    Classicos(R.string.classico),
    Esportivos(R.string.esportivos),
    Luxo(R.string.luxo)
}
