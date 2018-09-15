package br.com.livroandroid.carros.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.FavoritosService
import br.com.livroandroid.carros.extensions.loadUrl
import br.com.livroandroid.carros.extensions.setupToolbar
import br.com.livroandroid.carros.extensions.toast
import kotlinx.android.synthetic.main.activity_carro.*
import org.jetbrains.anko.doAsync

class CarroActivity : AppCompatActivity() {

    private val carro by lazy {
        intent.getSerializableExtra("carro") as Carro
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_carro)

        setupToolbar(R.id.toolbar, upNavigation = true)

        tNome.text = carro.nome

        img.loadUrl(carro.urlFoto)

        // Toca o Vídeo
        imgPlayVideo.setOnClickListener { onClickPlayVideo() }
    }

    private fun onClickPlayVideo() {
        val url = carro.urlVideo
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "video/*")
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carro, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_favoritar) {
            doAsync {
                val b = FavoritosService.favoritar(carro)

                toast("Carro favoritado $b")
            }
        } else if(item?.itemId == R.id.action_deletar) {
            doAsync {
                val response= CarroService.delete(carro)

                toast(response.msg)

                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
