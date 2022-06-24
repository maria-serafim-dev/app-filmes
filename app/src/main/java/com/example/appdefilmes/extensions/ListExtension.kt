package com.example.appdefilmes.extensions

import com.example.appdefilmes.model.Filme

fun List<Filme>.reduzirParaTamanhoDez(): List<Filme>{
    return this.dropLast(this.size - 10)
}

fun List<Filme>.reduzirParaTamanhoNove(): List<Filme>{
    return this.dropLast(this.size - 9)
}