// MinhasViagensActivity.kt
package com.example.viagens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.viagens.databinding.ActivityMinhasViagensBinding
import com.example.viagens.databinding.DetalhesDasViagensBinding

class MinhasViagensActivity : AppCompatActivity() {

    // Binding para aceder aos elementos do layout
    private lateinit var binding: ActivityMinhasViagensBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinhasViagensBinding.inflate(layoutInflater) // Inicialize o binding
        setContentView(binding.root)

        // Botão "Voltar" (por agora está invisível)
        binding.backButton.setOnClickListener {
            // Termina a Activity
            finish()
        }

        // Botão "Adicionar"
        binding.addButton.setOnClickListener {
            Toast.makeText(this, "Adicionar nova viagem (ação a definir)", Toast.LENGTH_SHORT).show()
        }

        // Botões de filtro
        binding.filterMelhorClassificacao.setOnClickListener {
            aplicarFiltro("melhor_classificacao")
        }

        binding.filterMaisRecente.setOnClickListener {
            aplicarFiltro("mais_recente")
        }

        binding.filterMaisAntiga.setOnClickListener {
            aplicarFiltro("mais_antiga")
        }

        // Botão "Ver Detalhes" da viagem para as Bahamas
        binding.verDetalhesBahamas.setOnClickListener {
            val intent = Intent(this, DetalhesDasViagensBinding::class.java) // Use a classe que você criou
            intent.putExtra("destino", "Bahamas")
            startActivity(intent)
        }

        // TODO: Repetir lógica para os outros cartões (quando forem adicionados)
    }

    private fun aplicarFiltro(tipo: String) {
        when (tipo) {
            "melhor_classificacao" -> Toast.makeText(this, "Filtrar por melhor classificação", Toast.LENGTH_SHORT).show()
            "mais_recente" -> Toast.makeText(this, "Filtrar por mais recente", Toast.LENGTH_SHORT).show()
            "mais_antiga" -> Toast.makeText(this, "Filtrar por mais antiga", Toast.LENGTH_SHORT).show()
        }

        // Aqui poderias recarregar a lista de viagens de acordo com o filtro
    }
}
