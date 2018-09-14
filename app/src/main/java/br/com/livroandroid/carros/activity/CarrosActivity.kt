package br.com.livroandroid.carros.activity

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.R.string.carros
import br.com.livroandroid.carros.adapter.CarroAdapter
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.extensions.setupToolbar
import br.com.livroandroid.carros.extensions.toast
import kotlinx.android.synthetic.main.activity_carros.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class CarrosActivity : BaseActivity() {
    private var tipo: TipoCarro = TipoCarro.Classicos
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)

        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro
        val s = context.getString(tipo.string)

        setupToolbar(R.id.toolbar, s, upNavigation = true)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        Thread {
            val carros = CarroService.getCarros(tipo)
            runOnUiThread {
                recyclerView.adapter = CarroAdapter(carros) {
                    c -> onClickCarro(c)
                }
            }
        }.start()

        /*doAsync {
            val carros = CarroService.getCarros(tipo)

            uiThread {

                recyclerView.adapter = CarroAdapter(carros) {
                    c -> onClickCarro(c)
                }
            }
        }*/


    }

    private fun onClickCarro(c: Carro) {
        toast(c.nome)
    }
}