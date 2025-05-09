package com.example.viagens

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.viagens.data.model.Viagem
import com.example.viagens.databinding.ActivityNovaViagemBinding
import com.example.viagens.ui.viewmodel.ViagemViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NovaViagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovaViagemBinding
    private lateinit var viewModel: ViagemViewModel
    private var imagemUri: Uri? = null
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Launcher para selecionar imagem da galeria
    private val selecionarImagemLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imagemUri = result.data?.data
            imagemUri?.let {
                binding.imagemNovaViagem.setImageURI(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaViagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViagemViewModel::class.java]


        // Bottom Navigation
        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_notifications -> true // Já está na activity
                R.id.navigation_dashboard -> {
                    startActivity(Intent(this, GaleriaActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_notifications -> {
                    startActivity(Intent(this, NovaViagemActivity::class.java))
                    // Não chamar finish aqui
                    true
                }
                R.id.navigation_dashboard -> {
                    startActivity(Intent(this, GaleriaActivity::class.java))
                    // Não chamar finish aqui
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Configurar clique na imagem para selecionar da galeria
        binding.imagemNovaViagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            selecionarImagemLauncher.launch(intent)
        }

        // Configurar DatePicker para data de início
        binding.editDataInicio.setOnClickListener {
            showDatePicker { date ->
                binding.editDataInicio.setText(date)
            }
        }

        // Configurar DatePicker para data de fim
        binding.editDataFim.setOnClickListener {
            showDatePicker { date ->
                binding.editDataFim.setText(date)
            }
        }

        // Configurar botão salvar
        binding.btnSalvarNovaViagem.setOnClickListener {
            salvarViagem()
        }

        // Configurar botão voltar
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
                onDateSelected(dateFormat.format(selectedDate.time))
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun salvarViagem() {
        val destino = binding.editNomeDestino.text.toString().trim()
        val descricao = binding.editDescricao.text.toString().trim()
        val rating = binding.ratingBarNovaViagem.rating
        val dataInicio = binding.editDataInicio.text.toString().trim()
        val dataFim = binding.editDataFim.text.toString().trim()

        // Validação
        if (destino.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o destino", Toast.LENGTH_SHORT).show()
            return
        }
        if (descricao.isEmpty()) {
            Toast.makeText(this, "Por favor, insira a descrição", Toast.LENGTH_SHORT).show()
            return
        }
        if (rating == 0f) {
            Toast.makeText(this, "Por favor, selecione uma classificação", Toast.LENGTH_SHORT).show()
            return
        }
        if (dataInicio.isEmpty()) {
            Toast.makeText(this, "Por favor, insira a data de início", Toast.LENGTH_SHORT).show()
            return
        }
        if (dataFim.isEmpty()) {
            Toast.makeText(this, "Por favor, insira a data de fim", Toast.LENGTH_SHORT).show()
            return
        }

        // Criar objeto Viagem
        val viagem = Viagem(
            destino = destino,
            descricao = descricao,
            rating = rating,
            dataInicio = dataInicio,
            dataFim = dataFim,
            imagemUri = imagemUri?.toString() // Armazena o URI da imagem, se selecionada
        )

        // Salvar no banco de dados
        viewModel.inserirViagem(viagem)
        Toast.makeText(this, "Viagem salva com sucesso!", Toast.LENGTH_SHORT).show()
        finish() // Volta para a MinhasViagensActivity
    }
}