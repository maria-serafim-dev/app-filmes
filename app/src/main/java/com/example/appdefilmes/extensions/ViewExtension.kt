package com.example.appdefilmes.extensions

import android.widget.ImageView
import com.example.appdefilmes.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.animacao_carregando)
        .error(R.drawable.ic_imagem_quebrada)
        .into(this)
}