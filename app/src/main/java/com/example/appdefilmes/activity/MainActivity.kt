package com.example.appdefilmes.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_inicio) as NavHostFragment
        navController = navHostFragment.navController
        binding.conteudoMain.btNavegacaoInicio.setupWithNavController(navController)

        abrirToast(view)
        ouvinteItemSelecionadoDrawerNavigation()
        ouvinteMenuAppBar()
    }

    private fun ouvinteMenuAppBar() {
        binding.conteudoMain.topAppBar.setOnMenuItemClickListener {
            inicializarDrawerNavigation()
            false
        }
    }


    private fun ouvinteItemSelecionadoDrawerNavigation() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when(menuItem.itemId){
                R.id.item1 -> abrirConfiguracoes()
                R.id.item2 -> abrirPerguntasFrequentes()
                R.id.item3 -> abrirTermosPoliticas()
                R.id.item4 -> abrirSobre()
            }

            binding.drawerLayout.close()
            true
        }
    }

    private fun abrirSobre() {
        val intent = Intent(this, SobreActivity::class.java)
        startActivity(intent)
    }

    private fun abrirTermosPoliticas() {
        val queryUrl: Uri = Uri.parse("https://ajuda.globo/globoplay/termos-e-politicas/")
        val intent = Intent(Intent.ACTION_VIEW, queryUrl)
        startActivity(intent)
    }

    private fun abrirPerguntasFrequentes() {
        val queryUrl: Uri = Uri.parse("https://ajuda.globo/globoplay/")
        val intent = Intent(Intent.ACTION_VIEW, queryUrl)
        startActivity(intent)
    }

    private fun abrirConfiguracoes() {

    }

    private fun inicializarDrawerNavigation() {
        binding.drawerLayout.openDrawer(GravityCompat.START)

    }

    private fun abrirToast(view: View) {

        val nome = auth.currentUser?.displayName
        Toast.makeText(view.context, "Bem vinda $nome", Toast.LENGTH_LONG).show()
    }


}