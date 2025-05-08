package com.example.viagens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Encontra o botão pelo ID no layout
        val startButton = findViewById<Button>(R.id.start_button)

        // Define a ação de clique do botão
        startButton.setOnClickListener {
            // Cria uma Intent para abrir LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Opcional: remove MainActivity da pilha
        }
    }
}
