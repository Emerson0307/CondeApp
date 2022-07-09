package com.example.condeapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.condeapp.databinding.ActivityCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding // isso aqui é uma biblioteca de facilitar o instaciamento dos objetos
    private val auth = FirebaseAuth.getInstance() //variavel global para facilitar o codigo la embaixo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() // isso aqui só serve para tirar a barra feia que fica em cima do app

        binding.btnCadastrar.setOnClickListener { view ->
            val senha = binding.editSenha.text.toString() //Declaração de váriaveis
            val confirmasenha = binding.editConfirmaSenha.text.toString()
            val email = binding.editEmail.text.toString()
            val nome = binding.editNome.text.toString()

            if (senha != confirmasenha) {
                binding.editConfirmaSenha.error = "A senha e a confimação de senha devem ser iguais!"
            }
            if (nome.isEmpty()) {
                binding.editNome.error = "Insira o nome!"
            }
            if (email.isEmpty()) {
                binding.editEmail.error = "Insira o email!"
            }
            if (senha.isEmpty()) {
                binding.editSenha.error = "Insira a senha!"
            }
            if (confirmasenha.isEmpty()) {
                binding.editConfirmaSenha.error = "Confirme a senha!"
            }
            if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()
                && confirmasenha.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, senha) // aqui é onde é feito o cadastro do usuario
                    .addOnCompleteListener { cadastro ->
                            if (cadastro.isSuccessful) {
                                SalvarDadosUsuario() // chamando a função que está lá embaixo
                                val snackbar = Snackbar.make(         // isso aqui é para mostrar um avisinho embaixo na tela
                                    view,
                                    "Cadastro realizado com sucesso, faça o login!",
                                    Snackbar.LENGTH_SHORT
                                )
                                snackbar.setBackgroundTint(Color.parseColor("#003400"))
                                snackbar.setTextColor(Color.WHITE)
                                snackbar.show()
                                binding.editNome.setText("")// isso aqui faz com que todos os campos se esvaziem dps de cada cadastro
                                binding.editEmail.setText("")
                                binding.editSenha.setText("")
                                binding.editConfirmaSenha.setText("")
                                IrParaPrimeiraTela()//chamando a função para voltar para MainActivity depois de cadastrar
                            }
                    }.addOnFailureListener{exception ->
                        //Aqui são só as exceptions. O que acontesse se caso cada coisinha não saia como esperado, ele imprime uma mensagem em baixo
                        val mensagemErro = when (exception) {
                            is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                            is FirebaseAuthInvalidCredentialsException -> "Digite um email válido!"
                            is FirebaseAuthUserCollisionException -> "Esta conta já foi cadastrada!"
                            is FirebaseNetworkException -> "Sem conexão com a internet!"
                            else -> "Erro ao cadastrar usuário!"
                        }
                        val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.parseColor("#003400"))
                        snackbar.setTextColor(Color.WHITE)
                        snackbar.show()
                }
            }
        }
    }

    private fun SalvarDadosUsuario(){ //Essa é uma funçãozinha para os id e salvar o nome dos usuarios no firebase
       val nome = binding.editNome.text.toString()

        val db = FirebaseFirestore.getInstance()

        val usuarios = hashMapOf<String,Any?>()
        usuarios.put("nome", nome)

        val usuarioID = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Usuarios").document(usuarioID).set(usuarios)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Log.d("db", "Sucesso ao salvar os dados")
                }else{
                    Log.d("db_error", "Erro ao salvar os dados")
                }
        }

    }

    private fun IrParaPrimeiraTela() {//Função para mudar para o MainActivity quando é chamada
        val PrimeiraTela = Intent(this, MainActivity::class.java)
        startActivity(PrimeiraTela)
    }
}

