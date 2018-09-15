package br.com.livroandroid.carros.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.adapter.CarroAdapter
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.extensions.toast
import kotlinx.android.synthetic.main.fragments_carros.*
import kotlinx.android.synthetic.main.include_progress.*
import java.lang.Exception

class CarrosFragment : Fragment() {

    private lateinit var tipo: TipoCarro

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragments_carros, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tipo = arguments?.getSerializable("tipo") as TipoCarro

        // Init Views
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()

        swipeToRefresh.setOnRefreshListener {
            taskCarros()
        }
        swipeToRefresh.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_carros, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_about -> {
                toast("About")
                return true
            }
            R.id.action_refresh-> {
//                taskCarros()
                toast("Refresh")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        taskCarros()

    }

    private fun taskCarros() {

        if(! swipeToRefresh.isRefreshing) {
            progress.visibility = View.VISIBLE
        }

        Thread {
            try {
                val carros = CarroService.getCarros(tipo)

                activity?.runOnUiThread {
                    recyclerView.adapter = CarroAdapter(carros) { c ->
                        onClickCarro(c)
                    }

                    swipeToRefresh.isRefreshing = false
                    progress.visibility = View.INVISIBLE
                }
            } catch (ex: Exception) {
                toast("ERRO!")
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
