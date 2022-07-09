package com.example.condeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ProdutoAdapter(contexto: Context) : ArrayAdapter<Produto>(contexto, 0) {
//Pode estar aqui tbm o problema aqui eu me fiz parecido com o do professor tbm mas não ta dando certo
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val v: View
        if (convertView != null) {
            v = convertView
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false)
        }
        val item = getItem(position)
        val txtProduto = v.findViewById<TextView>(R.id.txtProduto)
        val imgProduto = v.findViewById<ImageView>(R.id.iconeInserirImagem)
        val txtDescricao = v.findViewById<TextView>(R.id.txtTipo)

        txtProduto.text = item?.nome
        txtDescricao.text = item?.descricao

        if (item?.foto != null) {
            imgProduto.setImageBitmap(item.foto)
        }
        return v
    }

}