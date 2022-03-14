package com.example.appdefilmes.adapters.recyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.R
import com.example.appdefilmes.model.Filme
import com.squareup.picasso.Picasso
import java.io.File


class FilmeAdapter(var context: Context, var filmes: List<Filme>?) :
    RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>() {

    private val urlDaImagem = "https://image.tmdb.org/t/p/w500"
    private var onClick: InterfaceOnClick? = null

    fun setOnClick(onItemClickListener: InterfaceOnClick?) {
        this.onClick = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val view =
            LayoutInflater.from(this.context).inflate(R.layout.item_imagem_filme, parent, false)
        return FilmeViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme: Filme? = this.filmes?.get(position)

        if (filme != null) {
            Picasso.get().load(urlDaImagem + filme.poster_path).into(holder.imagem)
        }

        holder.imagem.setOnClickListener{
            if (filme != null) {
                onClick?.onItemClick(filme)
            }
        }
    }

    override fun getItemCount(): Int {
        return this.filmes?.size ?: 0
    }

    class FilmeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imagem: ImageView = view.findViewById(R.id.imagemReplica)

    }

}