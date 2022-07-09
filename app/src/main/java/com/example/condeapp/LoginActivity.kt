package com.example.condeapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.condeapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val textCadastrar = findViewById<TextView>(R.id.textCadastro)

        textCadastrar.setOnClickListener {
            IrParaTelaCadastro()
        }
        //essa parte aqui é onde ocorre o login
        binding.btnEntrar.setOnClickListener{ view ->

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val progressbar = binding.progressBar

            if (email.isEmpty()) {
                binding.editEmail.error = "Insira o email!"
            }
            if (senha.isEmpty()) {
                binding.editSenha.error = "Insira a senha!"
            }else{
                auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener{ autenticacao ->
                    if (autenticacao.isSuccessful){
                        progressbar.isVisible = true

                        Handler().postDelayed({
                            IrParaTelaPrincipal()
                        }, 2000)
                    }
                    //aqui é as exceptions do login
                }.addOnFailureListener { exception ->
                    val snackbar = Snackbar.make(view, "Não foi possível efetuar o login!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.parseColor("#003400"))
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                    }
                }
            }
        }
    //função para ir pra tela de Cadastro
        private fun IrParaTelaCadastro() {
            val TelaCadastro = Intent(this, CadastroActivity::class.java)
            startActivity(TelaCadastro)
        }
    //função para ir para tela principal (feed)
        private fun IrParaTelaPrincipal() {
            val TelaPrincipal = Intent(this, TelaPrincipalActivity::class.java)
            startActivity(TelaPrincipal)
        }

    //aqui é uma configuraçãozinha para quando sair do app e voltar o usuario continue logado
    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null){
            IrParaTelaPrincipal()
        }
    }
}

