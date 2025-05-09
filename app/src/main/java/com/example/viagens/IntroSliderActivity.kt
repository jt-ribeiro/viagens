package com.example.viagens

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.viagens.databinding.ActivityIntroSliderBinding
import com.example.viagens.ui.adapter.SliderPagerAdapter

class IntroSliderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroSliderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroSliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = SliderPagerAdapter(this)
        binding.viewPager.adapter = adapter

        setupIndicators()
        setCurrentIndicator(0)

        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)

                // Mudando texto do botão "Próximo" para "Começar" no último slide
                if (position == adapter.itemCount - 1) {
                    // Você pode adicionar um listener para a navegação final aqui
                    // para navegar para MainActivity ou outra atividade.
                    // Exemplo: startActivity(Intent(this@IntroSliderActivity, MainActivity::class.java))
                }
            }
        })
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(3)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }

        for (i in indicators.indices) {
            indicators[i] = ImageView(this).apply {
                setImageResource(R.drawable.indicator_inactive)
                this.layoutParams = layoutParams
            }
            binding.indicatorContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorContainer.getChildAt(i) as ImageView
            imageView.setImageResource(
                if (i == index) R.drawable.indicator_active
                else R.drawable.indicator_inactive
            )
        }
    }
}
