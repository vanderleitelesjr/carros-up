package br.com.livroandroid.carros.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.extensions.setupToolbar
import br.com.livroandroid.carros.extensions.toast
import kotlinx.android.synthetic.main.activity_carro_form.*
import org.jetbrains.anko.doAsync

class CarroFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro_form)

        setupToolbar(R.id.toolbar, upNavigation = true)

        btSalvar.setOnClickListener { onClickSalvar() }
    }

    private fun onClickSalvar() {

        doAsync {
            val c = Carro()
            c.nome = tNome.text.toString()
            c.desc = tDesc.text.toString()
            c.tipo = TipoCarro.Classicos.name.toLowerCase()

            CarroService.save(c)

            toast("Carro salvo com sucesso")
        }
    }
}
