package com.example.appdefilmes.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.appdefilmes.R
import com.example.appdefilmes.databinding.ActivityMainBinding
import com.example.appdefilmes.extensions.loadImage
import com.example.appdefilmes.model.UsuarioLogin
import com.example.appdefilmes.viewModel.UsuarioViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.divider.MaterialDivider
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val auth = FirebaseAuth.getInstance()
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val viewModel : UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        inicializarFragments()
        ouvinteItemSelecionadoDrawerNavigation()
        ouvinteMenuAppBar()

    }

    private fun inicializarFragments() {

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_inicio) as NavHostFragment
        navController = navHostFragment.navController
        binding.conteudoMain.bottomNavegacaoInicio.setupWithNavController(navController)

        viewModel.logado.observe(this) { usuarioLogado ->
            if (!usuarioLogado) {
                configurarHeaderDrawerSemLogin()
                inicializarFotoTopBarSemLogin()
            } else {
                viewModel.recuperarDadosUsuario()
                configuracoesUsuarioLogado()
            }
        }
    }

    private fun inicializarFotoTopBarSemLogin() {
        binding.conteudoMain.topAppBar.menu.findItem(R.id.item_app_bar).icon = ContextCompat.getDrawable(this, R.drawable.ic_usuario)
    }

    private fun configuracoesUsuarioLogado() {
        abrirToast()

        viewModel.usuarioLogado.observe(this) { usuario ->
            configurarHeaderDrawerComLogin(usuario)
            inicializarFotoTopBar(usuario)
        }
        inicializarGoogle()
    }

    private fun inicializarFotoTopBar(usuario: UsuarioLogin) {
        Glide.with(this).asDrawable().load(usuario.foto.toString()).circleCrop().into(object : CustomTarget<Drawable?>() {
            override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable?>?
            ) {
                binding.conteudoMain.topAppBar.menu.findItem(R.id.item_app_bar).icon = resource
            }
        })
    }

    private fun configurarHeaderDrawerComLogin(usuario: UsuarioLogin) {

        binding.navigationView.menu.findItem(R.id.item_sair).isVisible = true

        val header = binding.navigationView.getHeaderView(0)
        val nome: TextView = header.findViewById(R.id.tv_nome)
        val email: TextView = header.findViewById(R.id.tv_email)
        val imagem: ImageView = header.findViewById(R.id.img_perfil)
        val imagemArrow: ImageView = header.findViewById(R.id.img_arrow)
        val layoutButtonAssinante: LinearLayout = header.findViewById(R.id.layout_button_assinante)
        val layoutButtonsAssinante: LinearLayout = header.findViewById(R.id.layout_buttons_assinante)

        configurarHeaderDrawerPadrao(header, true, nome, email)
        nome.text = usuario.nome
        email.text = usuario.email

        imagem.loadImage(usuario.foto.toString())

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

    private fun configurarHeaderDrawerPadrao(
        header: View,
        b: Boolean,
        nome: TextView,
        email: TextView
    ) {

        val assinante: TextView = header.findViewById(R.id.tv_assinante)
        val modoInfantil: Button = header.findViewById(R.id.btn_modo_infantil)
        val maisPlanos: Button = header.findViewById(R.id.btn_mais_planos)
        val sejaAssinante: Button = header.findViewById(R.id.btn_seja_assinante)
        val divider: MaterialDivider = header.findViewById(R.id.divider_assinante)
        val entrar: TextView = header.findViewById(R.id.tv_entrar)
        val imagemArrow: ImageView = header.findViewById(R.id.img_arrow)

        var visibilidade : Int = View.GONE
        var visibilidadeBotoes : Int = View.VISIBLE
        if(b) {
            visibilidade = View.VISIBLE
            visibilidadeBotoes = View.GONE
            header.setBackgroundColor(ContextCompat.getColor(this, R.color.vermelho_escuro))
        }else{
            header.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_fundo))
        }

        nome.visibility = visibilidade
        email.visibility = visibilidade
        modoInfantil.visibility = visibilidade
        assinante.visibility = visibilidade
        divider.visibility = visibilidade
        maisPlanos.visibility = visibilidade
        imagemArrow.visibility = visibilidade

        entrar.visibility = visibilidadeBotoes
        sejaAssinante.visibility = visibilidadeBotoes

    }
    private fun configurarHeaderDrawerSemLogin() {
        binding.navigationView.menu.findItem(R.id.item_sair).isVisible = false

        val header = binding.navigationView.getHeaderView(0)
        val nome: TextView = header.findViewById(R.id.tv_nome)
        val email: TextView = header.findViewById(R.id.tv_email)

        configurarHeaderDrawerPadrao(header, false, nome, email)

        val imagem: ImageView = header.findViewById(R.id.img_perfil)
        imagem.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_usuario))

        ouvinteBotaoEntrar(header)
        ouvinteBotaoSejaAssinante(header)
    }

    private fun ouvinteBotaoEntrar(header: View) {
        header.findViewById<Button>(R.id.tv_entrar).setOnClickListener {
            binding.drawerLayout.close()
            navController.navigate(R.id.loginFragment)
        }
    }

    private fun ouvinteBotaoSejaAssinante(header: View) {
        header.findViewById<Button>(R.id.btn_seja_assinante).setOnClickListener {
            binding.drawerLayout.close()
            val queryUrl: Uri = Uri.parse("https://login.globo.com/cadastro/4654?platform=android-app&url=https%3A%2F%2Fdevices.globoid.globo.com%2Fauth%2F4654%2F7c5c4a0d-61ef-4891-90ae-52f83c466389")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            startActivity(intent)
        }
    }

    private fun ouvinteMenuAppBar() {
        binding.conteudoMain.topAppBar.setOnMenuItemClickListener {
            inicializarDrawerNavigation()
            false
        }
    }

    private fun inicializarGoogle() {
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
                R.id.item_sair -> signOutTodosProvedores()
            }

            binding.drawerLayout.close()
            true
        }


    }

    private fun signOutTodosProvedores() {
        viewModel.signOutTodosProvedores(mGoogleSignInClient!!)
        viewModel.logado.value = false
        voltarParaHome()
    }
    private fun voltarParaHome() {
        val startDestination = navController.graph.startDestinationId
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()
        navController.navigate(startDestination, null, navOptions)
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