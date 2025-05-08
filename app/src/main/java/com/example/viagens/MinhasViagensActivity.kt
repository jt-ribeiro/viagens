package com.example.viagens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.viagens.databinding.ActivityMinhasViagensBinding

class MinhasViagensActivity : AppCompatActivity() {

    // Binding para acessar os elementos do layout
    private lateinit var binding: ActivityMinhasViagensBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinhasViagensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)

        // Botão de logout (se você adicionou no XML como ImageView ou Button)
        binding.logoutButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            // Navegar para LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        // Botão "Voltar" (por agora está invisível)
        binding.backButton.setOnClickListener {
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

        // Mostrar detalhes das viagens na mesma tela
        binding.verDetalhesBahamas.setOnClickListener {
            mostrarDetalhesDaViagem("Bahamas")
        }

        binding.verDetalhesSantorini.setOnClickListener {
            mostrarDetalhesDaViagem("Santorini")
        }

        binding.verDetalhesCaboVerde.setOnClickListener {
            mostrarDetalhesDaViagem("Cabo Verde")
        }
    }

    private fun aplicarFiltro(tipo: String) {
        val mensagem = when (tipo) {
            "melhor_classificacao" -> "Filtrar por melhor classificação"
            "mais_recente" -> "Filtrar por mais recente"
            "mais_antiga" -> "Filtrar por mais antiga"
            else -> "Filtro desconhecido"
        }

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()

        // Aqui você pode recarregar a lista ou ordenar conforme o filtro
    }

    private fun mostrarDetalhesDaViagem(destino: String) {
        // Exemplo simples: mostra informações sobre a viagem
        binding.detalhesContainer.removeAllViews()

        val descricao = TextView(this).apply {
            text = when (destino) {
                "Bahamas" -> "Relaxe nas praias brancas e águas cristalinas das Bahamas."
                "Santorini" -> "Explore as ruínas antigas e paisagens deslumbrantes de Santorini."
                "Cabo Verde" -> "Desfrute das praias tropicais e cultura vibrante de Cabo Verde."
                else -> "Detalhes sobre $destino ainda não disponíveis."
            }
            textSize = 16f
            setPadding(24, 24, 24, 24)
        }

        val btnFechar = Button(this).apply {
            text = "Fechar Detalhes"
            setBackgroundColor(getColor(android.R.color.holo_blue_light))
            setTextColor(getColor(android.R.color.white))
            setOnClickListener {
                binding.detalhesContainer.visibility = View.GONE
            }
        }

        binding.detalhesContainer.addView(descricao)
        binding.detalhesContainer.addView(btnFechar)
        binding.detalhesContainer.visibility = View.VISIBLE

        // Rola até a parte dos detalhes
        binding.scrollView.post {
            binding.scrollView.smoothScrollTo(0, binding.detalhesContainer.top)
        }
    }
}