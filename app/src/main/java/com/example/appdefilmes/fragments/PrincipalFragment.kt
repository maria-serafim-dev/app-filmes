package com.example.appdefilmes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.appdefilmes.databinding.FragmentPrincipalBinding

class PrincipalFragment : Fragment() {

    private lateinit var binding: FragmentPrincipalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrincipalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ouvinteBotaoCadastrarMaisTarde()
        ouvinteBotaoLogin()
        ouvinteBotaoCadastrar()
    }

   private fun ouvinteBotaoCadastrarMaisTarde() {
        binding.btnCriarMaisTarde.setOnClickListener {
            val action = PrincipalFragmentDirections.actionPrincipalFragmentToInicioFragment2()
            proximaActivity(action)
        }
    }

    private fun ouvinteBotaoLogin() {
        binding.btnLogin.setOnClickListener {
            val action = PrincipalFragmentDirections.actionPrincipalFragmentToLoginFragment()
            proximaActivity(action)
        }
    }

    private fun proximaActivity(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun ouvinteBotaoCadastrar() {
        binding.btnCadastrar.setOnClickListener {
            val action = PrincipalFragmentDirections.actionPrincipalFragmentToCadastroFragment()
            proximaActivity(action)
        }
    }



}