package com.example.viagens.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viagens.R
import com.example.viagens.data.model.Viagem
import com.example.viagens.databinding.ItemViagemBinding

class ViagemAdapter(
    private val viagens: List<Viagem>,
    private val onVerDetalhesClick: (Viagem) -> Unit
) : RecyclerView.Adapter<ViagemAdapter.ViagemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViagemViewHolder {
        val binding = ItemViagemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViagemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViagemViewHolder, position: Int) {
        holder.bind(viagens[position])
    }

    override fun getItemCount(): Int = viagens.size

    inner class ViagemViewHolder(private val binding: ItemViagemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viagem: Viagem) {
            binding.tvDestino.text = viagem.destino
            binding.ratingBar.rating = viagem.rating
            Glide.with(binding.ivImagem.context)
                .load(viagem.imagemUri)
                .placeholder(R.drawable.background_main)
                .error(R.drawable.background_main)
                .into(binding.ivImagem)
            binding.btnVerDetalhes.setOnClickListener {
                onVerDetalhesClick(viagem)
            }
        }
    }
}


