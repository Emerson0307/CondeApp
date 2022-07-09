package com.example.condeapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.condeapp.databinding.ActivityCadastroAnuncioBinding
import kotlinx.android.synthetic.main.activity_cadastro_anuncio.*

class CadastroAnuncioActivity : AppCompatActivity() {
   //basicamente é esse código, o Produto e o ProdutoAdapter que não estão funcionando direito
    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null
    private lateinit var binding: ActivityCadastroAnuncioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroAnuncioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //isso daqui é a configuração do spinner
        binding.spnProdServ.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
        listOf("Produto", "Serviço", "Produto/Serviço"))

        //aqui eu estava tentando fazer alguma coisa para funcionar, utilizando os materiais do professor e o git dele
        binding.btnAnunciarProduto.setOnClickListener {
            val ps = spnProdServ.selectedItem as Bitmap
            val produto = txtProduto.text.toString()
            val descricao = txtTipo.text.toString()
            if (produto.isNotEmpty() && descricao.isNotEmpty()) {
                val prod = Produto(produto, descricao, ps)
                produtosGlobal.add(prod)
                binding.txtProduto.text.clear()
                binding.txtTipo.text.clear()
            } else {
                    binding.txtProduto.error =
                        if (binding.txtProduto.text.isEmpty()) "Preencha o nome do produto ou serviço"
                        else null

                    binding.txtTipo.error =
                        if (binding.txtTipo.text.isEmpty()) "Descreva do produto ou serviço"
                        else null
                }
            }
        //para abrir a galeria
        binding.iconeInserirImagem.setOnClickListener {
            abrirGaleria()
        }
    }
    //função que é chamada ali em cima para abrir a galeria
    fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.type = "image/*"

        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                binding.iconeInserirImagem.setImageBitmap(imageBitMap)
            }
        }
    }
}