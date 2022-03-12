package com.example.appdefilmes.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.fragments.InicioFragment
import com.example.appdefilmes.fragments.MinhaListaFragment
import com.example.appdefilmes.model.ApiKey
import com.example.appdefilmes.model.Result
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeRetrofit
import com.example.appdefilmes.retrofit.service.FilmeService
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val BASE_URL: String = "https://api.themoviedb.org"
    val API_KEY: String = ApiKey().apiKey
    val CATEGORY: String = "popular"
    val LANGUAGE: String = "pt-BR"
    val ID_FILME: Int = 634649
    val PAGE: Int = 1

    var umFilme: Filme? = null
    var listaFilmes: List<Filme>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        consumirApiTodosFilmes()
        consumirApiUmFilme()

        val fragment = InicioFragment()
        if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment)
                    .commit()
        }

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener(selecionarMenu)

    }

    private fun consumirApiUmFilme() {
        val retrofit = FilmeRetrofit.getRetrofitInstance(BASE_URL)
        val endpoint = retrofit.create(FilmeService::class.java)
        val callback = endpoint.getFilmeId(ID_FILME, API_KEY, LANGUAGE)


       callback.enqueue(object : Callback<Filme> {
            override fun onResponse(call: Call<Filme>, response: Response<Filme>) {

                val results: Filme? = response.body()

                if (results != null) {
                    umFilme = results
                }
            }

            override fun onFailure(call: Call<Filme>, t: Throwable) {
                Log.i("Retrofit", t.message.toString())
            }
        })
    }


    private fun consumirApiTodosFilmes() {
        val retrofit = FilmeRetrofit.getRetrofitInstance(BASE_URL)
        val endpoint = retrofit.create(FilmeService::class.java)

        val callback = endpoint.getFilmes(CATEGORY, API_KEY, LANGUAGE, PAGE)
        callback.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {

                val results: Result? = response.body()
                listaFilmes = results?.results

                val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                recyclerView.adapter = listaFilmes?.let { FilmeAdapter(applicationContext, it) }
                val layoutManagerRecyclerView = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.layoutManager = layoutManagerRecyclerView

            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.i("Retrofit", t.message.toString())
            }

        })

    }

    private val selecionarMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.page_1 -> {
                val fragment = InicioFragment()
                iniciarFragment(fragment)
            }
            R.id.page_2 -> {
                val fragment = MinhaListaFragment()
                iniciarFragment(fragment)
            }
            R.id.page_3 -> {
                val intent = Intent(this, InformacoesFilmeActivity::class.java)
                intent.putExtra("filme", umFilme)
                startActivity(intent)
            }
            else -> false
        }
        true
    }

    private fun iniciarFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.commit()
    }

}