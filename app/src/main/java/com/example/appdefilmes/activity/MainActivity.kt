package com.example.appdefilmes.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.appdefilmes.R
import com.example.appdefilmes.fragments.InicioFragment
import com.example.appdefilmes.fragments.MinhaListaFragment
import com.example.appdefilmes.model.ApiKey
import com.example.appdefilmes.model.UmFilme
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

    var umFilme: UmFilme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        consumirApi()

        val fragment = InicioFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment)
                .commit()
        }

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener(selecionarMenu)

    }

    private fun consumirApi() {
        val retrofit = FilmeRetrofit.getRetrofitInstance(BASE_URL)
        val endpoint = retrofit.create(FilmeService::class.java)
        //val callback = endpoint.getFilmes(CATEGORY, API_KEY, LANGUAGE)
        val callback = endpoint.getFilmeId(ID_FILME, API_KEY, LANGUAGE)


       callback.enqueue(object : Callback<UmFilme> {
            override fun onResponse(call: Call<UmFilme>, response: Response<UmFilme>) {

                val results: UmFilme? = response.body()

                if (results != null) {
                    results.title?.let { Log.i("Retrofit", it) }
                    umFilme = UmFilme(
                        results.backdrop_path,
                        results.id,
                        results.original_language,
                        results.original_title,
                        results.overview,
                        results.poster_path,
                        results.release_date,
                        results.title,
                        results.vote_average,
                        results.vote_count)


                }
            }

            override fun onFailure(call: Call<UmFilme>, t: Throwable) {
                Log.i("Retrofit", t.message.toString())
            }

        })


        /*callback.enqueue(object : Callback<Filme> {
            override fun onResponse(call: Call<Filme>, response: Response<Filme>) {

                val resusts: Filme? = response.body()
                val listaFilmes: List<Result>? = resusts?.results

                listaFilmes?.forEach{
                    Log.i("Retrofit", it.title)
                    Log.i("Retrofit", it.id.toString())
                    Log.i("Retrofit", it.overview)
                    Log.i("Retrofit", it.backdrop_path)
                    Log.i("Retrofit", it.poster_path)
                    Log.i("Retrofit", it.original_language)
                    Log.i("Retrofit", it.original_title)
                    Log.i("Retrofit", it.vote_average.toString())
                    Log.i("Retrofit", it.vote_count.toString())
                    Log.i("Retrofit", it.release_date)
                }

                val imagem = findViewById<ImageView>(R.id.imagem_picasso)
                Picasso.get().load("https://image.tmdb.org/t/p/w500/9PbtCo5IIkd26WPQfZUpPyn6fTz.jpg").into(imagem)
            }

            override fun onFailure(call: Call<Filme>, t: Throwable) {
                Log.i("Retrofit", t.message.toString())
            }

        })*/
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