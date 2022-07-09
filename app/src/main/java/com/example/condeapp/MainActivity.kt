package com.example.condeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        val btnLogar = findViewById<Button>(R.id.btnLogin)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastro)
        //aqui só tem código para chamar telas, essa é a tela inicial do app
        btnLogar.setOnClickListener {
            val TelaLogin = Intent(this, LoginActivity::class.java)
            startActivity(TelaLogin)
        }

        btnCadastrar.setOnClickListener {
            val TelaCadastro = Intent(this, CadastroActivity::class.java)
            startActivity(TelaCadastro)
        }
    }
}
