package com.example.appdefilmes.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.R
import com.example.appdefilmes.adapters.recyclerview.adapter.FilmeAdapter
import com.example.appdefilmes.dao.FilmeDAO
import com.example.appdefilmes.fragments.InicioFragment
import com.example.appdefilmes.fragments.MinhaListaFragment
import com.example.appdefilmes.model.ApiKey
import com.example.appdefilmes.model.Result
import com.example.appdefilmes.model.Filme
import com.example.appdefilmes.retrofit.FilmeResponse
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
    var listaFilmes = MutableLiveData<List<Filme>>()
    var listaFilmesPopulares: MutableList<Filme>? = null
    var listaFilmesMutavel: List<Filme> = listOf()
    var adapter = FilmeAdapter(this, listaFilmesMutavel)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragment = InicioFragment()
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment)
                .commit()

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener(selecionarMenu)

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