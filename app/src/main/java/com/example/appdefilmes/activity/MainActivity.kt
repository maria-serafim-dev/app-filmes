package com.example.appdefilmes.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appdefilmes.R
import com.example.appdefilmes.dao.UsuarioDAO
import com.example.appdefilmes.databinding.ActivityMainBinding
import com.example.appdefilmes.fragments.InicioFragment
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.divider.MaterialDivider
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

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

        if (!UsuarioDAO().usuarioLogado) {
            val fragment = InicioFragment()
            if (savedInstanceState == null)
                supportFragmentManager.beginTransaction()
                    .add(binding.conteudoMain.fragmentInicio.id, fragment)
                    .commit()
            binding.conteudoMain.bottomNavegacaoInicio.menu.removeItem(R.id.minhaListaFragment2)

            configurarHeaderDrawerSemLogin()

        } else {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.fragment_inicio) as NavHostFragment
            navController = navHostFragment.navController
            binding.conteudoMain.bottomNavegacaoInicio.setupWithNavController(navController)

            abrirToast()
            configurarHeaderDrawer()
            initializarGoogle()
        }
    }


    private fun configurarHeaderDrawer() {
        val header = binding.navigationView.getHeaderView(0)
        val nome: TextView = header.findViewById(R.id.tv_nome)
        val email: TextView = header.findViewById(R.id.tv_email)
        val imagem: ImageView = header.findViewById(R.id.img_perfil)
        val imagemArrow: ImageView = header.findViewById(R.id.img_arrow)
        val layoutButtonAssinante: LinearLayout = header.findViewById(R.id.layout_button_assinante)
        val layoutButtonsAssinante: LinearLayout = header.findViewById(R.id.layout_buttons_assinante)

        nome.text = UsuarioDAO().usuarioNome
        email.text = UsuarioDAO().usuarioEmail

        Picasso.get().load(UsuarioDAO().usuarioFoto).into(imagem)

        layoutButtonAssinante.setOnClickListener {
            when(layoutButtonsAssinante.visibility){
                View.VISIBLE -> {
                    layoutButtonsAssinante.visibility = View.GONE
                    imagemArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_down))
                }
                View.GONE -> {
                    layoutButtonsAssinante.visibility = View.VISIBLE
                    imagemArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                }
                View.INVISIBLE -> {
                    layoutButtonsAssinante.visibility = View.VISIBLE
                    imagemArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                }
            }

        }
    }

    private fun configurarHeaderDrawerSemLogin() {
        binding.navigationView.menu.removeItem(R.id.item_sair)

        val header = binding.navigationView.getHeaderView(0)
        val nome: TextView = header.findViewById(R.id.tv_nome)
        val email: TextView = header.findViewById(R.id.tv_email)
        val entrar: TextView = header.findViewById(R.id.tv_entrar)
        val divider: MaterialDivider = header.findViewById(R.id.divider_assinante)
        val assinante: TextView = header.findViewById(R.id.tv_assinante)
        val modoInfantil: Button = header.findViewById(R.id.btn_modo_infantil)
        val maisPlanos: Button = header.findViewById(R.id.btn_mais_planos)
        val sejaAssinante: Button = header.findViewById(R.id.btn_seja_assinante)
        header.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_fundo))

        nome.visibility = View.GONE
        email.visibility = View.GONE
        modoInfantil.visibility = View.GONE
        assinante.visibility = View.GONE
        divider.visibility = View.GONE
        maisPlanos.visibility = View.GONE

        entrar.visibility = View.VISIBLE
        sejaAssinante.visibility = View.VISIBLE

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