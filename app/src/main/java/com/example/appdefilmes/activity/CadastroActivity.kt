package com.example.appdefilmes.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appdefilmes.R
import com.example.appdefilmes.data.*
import com.example.appdefilmes.databinding.ActivityCadastroBinding
import com.example.appdefilmes.model.Usuario
import com.example.appdefilmes.repository.UsuarioRepository
import com.example.appdefilmes.retrofit.UsuarioResponse
import com.google.android.material.datepicker.MaterialDatePicker

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private val repository : UsuarioRepository by lazy {
        UsuarioRepository()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciarInputsDropdown()

        val datePicker = inicializarMaterialDatePicker()

        ouvinteDataPicker(datePicker)
        ouvinteBotaoCadastrar()
    }

    private fun ouvinteBotaoCadastrar() {
        binding.btnCadastrar.setOnClickListener {
            if(validarCampos()){
                cadastrarUsuario()
            }
        }
    }

    private fun cadastrarUsuario() {

        val email = binding.editEmail.text.toString()
        val senha: String = binding.editSenha.text.toString()
        val nome = binding.editNome.text.toString()
        val dataNascimento = binding.editDataNascimento.text.toString()
        val cidade = binding.editCidade.text.toString()
        val genero = binding.tfGenero.editText?.text.toString()
        val estado = binding.tfEstado.editText?.text.toString()

        val usuario = Usuario(nome, email, senha, dataNascimento, genero, cidade, estado)

        repository.cadastrarUsuario(usuario,  object : UsuarioResponse {
            override fun resposta(resposta: Int) {
                when(resposta){
                    sucessoCadastro -> {
                        Toast.makeText(applicationContext, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                        proximaActivity()
                    }
                    erroEmail -> {
                        binding.tfEmail.error = "Já existe um usuário com esse e-mail"
                        binding.editEmail.requestFocus()
                    }
                    erroCadastro -> Toast.makeText(applicationContext, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun inicializarMaterialDatePicker(): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecione uma data de nascimento")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }

    private fun ouvinteDataPicker(datePicker: MaterialDatePicker<Long>) {
        binding.editDataNascimento.setOnClickListener {
            datePicker.show(supportFragmentManager, datePicker.tag)

            datePicker.addOnPositiveButtonClickListener {
                binding.editDataNascimento.setText(datePicker.headerText.toString())
            }
        }
    }

    private fun iniciarInputsDropdown() {
        val adapterGenero = ArrayAdapter(applicationContext, R.layout.lista_item_input, itensGenero)
        (binding.tfGenero.editText as? AutoCompleteTextView)?.setAdapter(adapterGenero)

        val adapterEstados =
            ArrayAdapter(applicationContext, R.layout.lista_item_input, itensEstadosBrasileiros)
        (binding.tfEstado.editText as? AutoCompleteTextView)?.setAdapter(adapterEstados)
    }

    private fun validarCampos(): Boolean {

        limparErros()

        var retorno = true

        if (TextUtils.isEmpty(binding.editNome.text) || binding.editNome.text == null) {
            binding.tfNome.error = getString(R.string.erro_input_nome)
            binding.editNome.requestFocus()
            retorno = false
        }

        if (TextUtils.isEmpty(binding.editSenha.text) || binding.editSenha.text == null) {
            binding.tfSenha.error = getString(R.string.erro_input_senha)
            binding.editSenha.requestFocus()
            retorno = false
        }

        if (TextUtils.isEmpty(binding.editEmail.text) || binding.editEmail.text == null) {
            binding.tfEmail.error = getString(R.string.erro_input_email)
            binding.editEmail.requestFocus()
            retorno = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.text.toString()).matches()) {
            binding.tfEmail.error = getString(R.string.erro_input_email_invalido)
            binding.editEmail.requestFocus()
            retorno = false
        }

        if (TextUtils.isEmpty(binding.editDataNascimento.text) || binding.editDataNascimento.text == null) {
            binding.tfDataNascimento.error = getString(R.string.erro_input_data)
            binding.editDataNascimento.requestFocus()
            retorno = false
        }

        if (TextUtils.isEmpty(binding.editCidade.text) || binding.editCidade.text == null) {
            binding.tfCidade.error = getString(R.string.erro_input_cidade)
            binding.editCidade.requestFocus()
            retorno = false
        }

        if (TextUtils.isEmpty(binding.tfGenero.editText?.text.toString())) {
            binding.tfGenero.error = getString(R.string.erro_input_genero)
            binding.tfGenero.requestFocus()
            retorno = false
        }

        if (TextUtils.isEmpty(binding.tfEstado.editText?.text.toString())) {
            binding.tfEstado.error = getString(R.string.erro_input_estado)
            binding.tfEstado.requestFocus()
            retorno = false
        }

        return retorno
    }

    private fun limparErros() {
        binding.tfNome.error = null
        binding.tfEmail.error = null
        binding.tfSenha.error = null
        binding.tfDataNascimento.error = null
        binding.tfGenero.error = null
        binding.tfCidade.error = null
        binding.tfEstado.error = null
    }


    private fun proximaActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}