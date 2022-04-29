package com.example.appdefilmes.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityMainBinding
import com.example.appdefilmes.fragments.InicioFragment
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val auth = FirebaseAuth.getInstance()
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        inicializarFragments(savedInstanceState)
        ouvinteItemSelecionadoDrawerNavigation()
        ouvinteMenuAppBar()
    }

    private fun inicializarFragments(savedInstanceState: Bundle?) {
        val extra = intent.getIntExtra("cadastrado", 0)

        if (extra == 0) {
            val fragment = InicioFragment()
            if (savedInstanceState == null)
                supportFragmentManager.beginTransaction()
                    .add(binding.conteudoMain.fragmentInicio.id, fragment)
                    .commit()
            binding.conteudoMain.bottomNavegacaoInicio.menu.removeItem(R.id.minhaListaFragment2)
            binding.navigationView.menu.removeItem(R.id.item_sair)

        } else {

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.fragment_inicio) as NavHostFragment
            navController = navHostFragment.navController
            binding.conteudoMain.bottomNavegacaoInicio.setupWithNavController(navController)

            abrirToast()
            initializarGoogle()
        }
    }

    private fun ouvinteMenuAppBar() {
        binding.conteudoMain.topAppBar.setOnMenuItemClickListener {
            inicializarDrawerNavigation()
            false
        }
    }

    private fun initializarGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun ouvinteItemSelecionadoDrawerNavigation() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when(menuItem.itemId){
                R.id.item_configuracao -> abrirConfiguracoes()
                R.id.item_perguntas -> abrirPerguntasFrequentes()
                R.id.item_termos_politicas -> abrirTermosPoliticas()
                R.id.item_sobre -> abrirSobre()
                R.id.item_sair -> loginTodasContas()
            }

            binding.drawerLayout.close()
            true
        }
    }

    private fun loginTodasContas() {
        logOutEmailSenha()
        logOutFacebook()
        logOutGoogle()
        voltarActivityPrincipal()
    }

    private fun logOutEmailSenha() {
        FirebaseAuth.getInstance().signOut()
    }

    private fun logOutFacebook() {
        LoginManager.getInstance().logOut()
    }

    private fun logOutGoogle() {
        mGoogleSignInClient!!.signOut()
    }

    private fun voltarActivityPrincipal() {
        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)
        finish()
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

    private fun abrirToast() {
        val nome = auth.currentUser?.displayName
        Toast.makeText(this, "Bem vinda $nome", Toast.LENGTH_LONG).show()
    }


}