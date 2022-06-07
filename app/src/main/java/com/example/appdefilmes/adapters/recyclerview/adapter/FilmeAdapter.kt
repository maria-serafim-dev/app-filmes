package com.example.appdefilmes.adapters.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.R
import com.example.appdefilmes.model.Filme
import com.squareup.picasso.Picasso


class FilmeAdapter(var context: Context, var layout: Int) :
    ListAdapter<Filme, FilmeAdapter.FilmeViewHolder>(FilmeCallback()) {

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
        val filme: Filme? = getItem(position)

        if (filme != null) {
            Picasso.get().load(urlDaImagem + filme.poster_path).into(holder.imagem)

            if (layout != 1) {
                var layout_img = holder.imagem.layoutParams as LinearLayout.LayoutParams
                layout_img.width = LayoutParams.WRAP_CONTENT
                if (layout == 2) {
                    holder.nomeFilme.visibility = View.VISIBLE
                    holder.nomeFilme.text = filme.title
                }
            }
        }

        holder.imagem.setOnClickListener{
            if (filme != null) {
                onClick?.onItemClick(filme)
            }
        }
    }

    class FilmeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imagem: ImageView = view.findViewById(R.id.img_capa_filme)
        val nomeFilme: TextView = view.findViewById(R.id.tv_nome_filme)

    }

    class FilmeCallback : DiffUtil.ItemCallback<Filme>(){
        override fun areItemsTheSame(oldItem: Filme, newItem: Filme): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Filme, newItem: Filme): Boolean {
            return oldItem.poster_path == newItem.poster_path
        }

    }
}