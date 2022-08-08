package com.example.appdefilmes.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.appdefilmes.R

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.animacao_carregando)
        .error(R.drawable.ic_imagem_quebrada)
        .into(this)
}