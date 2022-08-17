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
import androidx.drawerlayout.widget.DrawerLayout
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
import com.example.appdefilmes.fragments.LoginFragment
import com.example.appdefilmes.fragments.PrincipalFragment
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

        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) { success ->
                if (success) {
                    viewModel.recuperarDadosUsuario()
                    configuracoesUsuarioLogado()
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
                    binding.conteudoMain.topAppBar.visibility = View.VISIBLE
                    binding.conteudoMain.bottomNavegacaoInicio.visibility = View.VISIBLE
                }
            }

    }

    private fun inicializarFragments() {

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_inicio) as NavHostFragment
        navController = navHostFragment.navController

        viewModel.logado.observe(this) { usuarioLogado ->
            if (!usuarioLogado) {

                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                binding.conteudoMain.topAppBar.visibility = View.GONE
                binding.conteudoMain.bottomNavegacaoInicio.visibility = View.GONE

                navController.navigate(R.id.loginFragment)

            } else {
                configuracoesUsuarioLogado()
            }
        }
    }

    private fun configuracoesUsuarioSemLogin() {
        binding.conteudoMain.bottomNavegacaoInicio.menu.removeItem(R.id.minhaListaFragment2)
        configurarHeaderDrawerSemLogin()
    }

    private fun configuracoesUsuarioLogado() {
        navController.popBackStack()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.inicioFragment2, true)
            .build()
        navController.navigate(R.id.inicioFragment2, null, navOptions)
        binding.conteudoMain.bottomNavegacaoInicio.setupWithNavController(navController)

        abrirToast()

        viewModel.usuarioLogado.observe(this) { usuario ->
            configurarHeaderDrawer(usuario)
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

    private fun configurarHeaderDrawer(usuario: UsuarioLogin) {
        val header = binding.navigationView.getHeaderView(0)
        val nome: TextView = header.findViewById(R.id.tv_nome)
        val email: TextView = header.findViewById(R.id.tv_email)
        val imagem: ImageView = header.findViewById(R.id.img_perfil)
        val imagemArrow: ImageView = header.findViewById(R.id.img_arrow)
        val layoutButtonAssinante: LinearLayout = header.findViewById(R.id.layout_button_assinante)
        val layoutButtonsAssinante: LinearLayout = header.findViewById(R.id.layout_buttons_assinante)

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
        voltarActivityPrincipal()
    }

    private fun voltarActivityPrincipal() {
        val intent = Intent(this, PrincipalFragment::class.java)
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