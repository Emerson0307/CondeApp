package com.example.condeapp

import android.graphics.Bitmap
//isso daqui eu tentei copiar do professor, pode estar aqui o problema, acho que ele usou isso daqui junto com o ProdutoAdapter
//para fazer toda a parte de transformar os objetos do cadastro anuncio nos anuncios do feed
data class Produto(val nome: String, val descricao: String, val foto: Bitmap? = null)