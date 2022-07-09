package com.example.condeapp

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.condeapp.databinding.ActivityTelaPrincipalBinding
import kotlinx.android.synthetic.main.activity_tela_principal.*

class TelaPrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val produtosAdapter = ProdutoAdapter(this)
        val listViewProdutos = binding.listViewProdutos

        listViewProdutos.adapter = produtosAdapter

        // essas funções para baixo é para chamar telas
        binding.containerUser1.setOnClickListener {
            IrParaTelaPerfil()
        }

        binding.btnAnunciar.setOnClickListener {
            val TelaAnunciar = Intent(this, CadastroAnuncioActivity::class.java)
            startActivity(TelaAnunciar)
        }

        binding.listViewProdutos.setOnItemClickListener { adapterView: AdapterView<*>, view, position: Int, id ->
            val item = produtosAdapter.getItem(position)
            produtosAdapter.remove(item)
        }
    }
    private fun IrParaTelaPerfil() {
        val TelaPerfil = Intent(this, PerfilActivity::class.java)
        startActivity(TelaPerfil)
    }
    //essa funçãozinha eu vi com o código do professor tbm
    override fun onResume() {
        super.onResume()

        val adapter = listViewProdutos.adapter as ProdutoAdapter

        adapter.clear()
        adapter.addAll(produtosGlobal)
    }
}