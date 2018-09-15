package br.com.livroandroid.carros.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import br.com.livroandroid.carros.fragments.CarrosFragment
import kotlinx.android.synthetic.main.activity_carros.*
import kotlinx.android.synthetic.main.include_progress.*
import java.lang.Exception

class CarrosActivity : BaseActivity() {
    private var tipo: TipoCarro = TipoCarro.Classicos
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)

        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro
        val s = context.getString(tipo.abacaxi)

        setupToolbar(R.id.toolbar, s, upNavigation = true)

        if(savedInstanceState == null) {
            val t = supportFragmentManager.beginTransaction()

            val frag = CarrosFragment()
            frag.arguments = intent.extras

//            val bundle = Bundle()
//            bundle.putSerializable("tipo",tipo)
//            frag.arguments = bundle

            t.replace(R.id.frag,frag).commit()
        }
    }


}