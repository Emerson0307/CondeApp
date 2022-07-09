package com.example.condeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.condeapp.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore


class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //código para deslogar ao clicar em "sair"
        binding.btnSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val TelaLogin = Intent(this, LoginActivity::class.java)
            startActivity(TelaLogin)
            finish()
        }
    }

    //aqui é um códigozinho para chamar o nome e o email na tela do perfil, assim será automatico de
    // acordo com o nome que o usuario digitou do cadastro
    override fun onStart() {
        super.onStart()

        val email = FirebaseAuth.getInstance().currentUser!!.email
        val usuarioID = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Usuarios").document(usuarioID)
            .addSnapshotListener(EventListener { value, error ->
                if (value != null) {
                    binding.textNomeUsuario.setText(value.getString("nome"))
                    binding.textEmailUsuario.setText(email)
                }
            })
    }

}
