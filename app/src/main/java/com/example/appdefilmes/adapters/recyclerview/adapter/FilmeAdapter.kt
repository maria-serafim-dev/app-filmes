package com.example.appdefilmes.adapters.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appdefilmes.data.layoutInicio
import com.example.appdefilmes.data.layoutMinhaLista
import com.example.appdefilmes.data.urlDaImagem
import com.example.appdefilmes.databinding.ItemImagemFilmeBinding
import com.example.appdefilmes.model.Filme
import com.squareup.picasso.Picasso


class FilmeAdapter(var context: Context, private var layout: Int) :
    ListAdapter<Filme, FilmeAdapter.FilmeViewHolder>(FilmeCallback()) {

    private var onClick: InterfaceOnClick? = null
    private lateinit var binding: ItemImagemFilmeBinding

    fun setOnClick(onItemClickListener: InterfaceOnClick?) {
        this.onClick = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        binding = ItemImagemFilmeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme: Filme? = getItem(position)
        holder.onBind(filme, layout)
    }

    inner class FilmeViewHolder(private val binding: ItemImagemFilmeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(filme: Filme?, layout: Int) {
            if (filme != null) {
                with(binding) {
                    Picasso.get().load(urlDaImagem + filme.poster_path).into(imgCapaFilme)

                    if (layout != layoutInicio) {
                        val layoutImg = imgCapaFilme.layoutParams as LinearLayout.LayoutParams
                        layoutImg.width = LayoutParams.WRAP_CONTENT

                        if (layout == layoutMinhaLista) {
                            tvNomeFilme.visibility = View.VISIBLE
                            tvNomeFilme.text = filme.title
                        }
                    }

                    imgCapaFilme.setOnClickListener {
                        onClick?.onItemClick(filme)
                    }
                }
            }
        }
    }
}

class FilmeCallback : DiffUtil.ItemCallback<Filme>(){

    override fun areItemsTheSame(oldItem: Filme, newItem: Filme): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Filme, newItem: Filme): Boolean {
        return oldItem == newItem
    }

}
