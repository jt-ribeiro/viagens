package com.example.viagens.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viagens.R
import com.example.viagens.data.model.Viagem
import com.example.viagens.databinding.ItemViagemBinding

class ViagemAdapter(
    private var viagens: List<Viagem>,
    private val onClick: (Viagem) -> Unit
) : RecyclerView.Adapter<ViagemAdapter.ViagemViewHolder>() {

    inner class ViagemViewHolder(private val binding: ItemViagemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viagem: Viagem) {
            try {
                binding.textDestino.text = viagem.destino
                binding.ratingBarViagem.rating = viagem.rating
                Glide.with(binding.root.context)
                    .load(viagem.imagemUri?.takeIf { it.isNotEmpty() } ?: R.drawable.background_main)
                    .placeholder(R.drawable.background_main)
                    .error(R.drawable.background_main)
                    .into(binding.imageViagem)
                binding.root.setOnClickListener { onClick(viagem) }
            } catch (e: Exception) {
                Log.e("ViagemAdapter", "Erro ao bind viagem: ${viagem.destino}", e)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViagemViewHolder {
        val binding = ItemViagemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViagemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViagemViewHolder, position: Int) {
        holder.bind(viagens[position])
    }

    override fun getItemCount(): Int = viagens.size

    fun updateViagens(newViagens: List<Viagem>) {
        try {
            viagens = newViagens
            notifyDataSetChanged()
            Log.d("ViagemAdapter", "Viagens atualizadas: ${viagens.size}")
        } catch (e: Exception) {
            Log.e("ViagemAdapter", "Erro ao atualizar viagens", e)
        }
    }
}