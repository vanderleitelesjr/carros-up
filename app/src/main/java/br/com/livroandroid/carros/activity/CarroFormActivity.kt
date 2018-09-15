package br.com.livroandroid.carros.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.extensions.setupToolbar
import br.com.livroandroid.carros.extensions.toast
import br.com.livroandroid.carros.utils.CameraHelper
import kotlinx.android.synthetic.main.activity_carro_form.*
import org.jetbrains.anko.doAsync

class CarroFormActivity : AppCompatActivity() {
    private val camera = CameraHelper()

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        setContentView(R.layout.activity_carro_form)

        setupToolbar(R.id.toolbar, upNavigation = true)

        btSalvar.setOnClickListener { onClickSalvar() }

        img.setOnClickListener { onClickCamera() }

        // Inicia a camera
        camera.init(b)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Salva o estado do arquivo caso gire a tela
        camera.onSaveInstanceState(outState)
    }

    // Ao clicar na imagem do AppHeader abre a câmera
    private fun onClickCamera() {
        val ms = System.currentTimeMillis()
        // Nome do arquivo da foto
        val fileName = "foto_carro_$ms.jpg"
        // Abre a câmera
        val intent = camera.open(this, fileName)
        startActivityForResult(intent, 0)
    }

    private fun onClickSalvar() {

        doAsync {
            val c = Carro()
            c.nome = tNome.text.toString()
            c.desc = tDesc.text.toString()
            c.tipo = TipoCarro.Classicos.name.toLowerCase()

            // Se tiver foto, faz upload
            val file = camera.file
            if (file != null && file.exists()) {
                val response = CarroService.postFoto(file)
                if (response.isOk()) {
                    // Atualiza a URL da foto no carro
                    c.urlFoto = response.url
                }
            }

            CarroService.save(c)

            toast("Carro salvo com sucesso")
        }
    }

    // Lê a foto quando a câmera retornar
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            // Resize da imagem
            val bitmap = camera.getBitmap(600, 600)
            if (bitmap != null) {
                // Salva arquivo neste tamanho
                camera.save(bitmap)
                // Mostra a foto do carro
                img.setImageBitmap(bitmap)
            }
        }
    }
}
