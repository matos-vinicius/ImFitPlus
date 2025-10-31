package br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus.databinding.ActivityResultadoImcBinding

class ResultadoImcActivity : AppCompatActivity() {

    private val binding: ActivityResultadoImcBinding by lazy {
        ActivityResultadoImcBinding.inflate(layoutInflater)
    }

    private lateinit var dadosPessoais: DadosPessoais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.imc_titulo)

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constantes.EXTRA_DADOS_PESSOAIS, DadosPessoais::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Constantes.EXTRA_DADOS_PESSOAIS)
        } ?: return

        exibirResultados()

        binding.btnCalcularGasto.setOnClickListener {
            val intent = Intent(this, GastoCaloricoActivity::class.java).apply {
                putExtra(Constantes.EXTRA_DADOS_PESSOAIS, dadosPessoais)
            }
            startActivity(intent)
        }

        binding.btnVoltarImc.setOnClickListener {
            finish()
        }
    }

    private fun exibirResultados() {
        val imc = dadosPessoais.imc ?: 0.0
        val (categoria, drawableId) = getCategoriaImc(imc)

        binding.tvSaudacao.text = getString(R.string.imc_saudacao, dadosPessoais.nome)
        binding.tvResultadoImc.text = getString(R.string.imc_resultado, imc)
        binding.tvCategoriaImc.text = getString(R.string.imc_categoria, categoria)

        binding.ivCategoriaImc.setImageDrawable(ContextCompat.getDrawable(this, drawableId))
    }

    private fun getCategoriaImc(imc: Double): Pair<String, Int> {
        return when {
            imc < 18.5 -> Pair(
                getString(R.string.imc_abaixo_peso),
                R.drawable.ic_abaixo_peso
            )
            imc < 25 -> Pair(
                getString(R.string.imc_normal),
                R.drawable.ic_normal
            )
            imc < 30 -> Pair(
                getString(R.string.imc_sobrepeso),
                R.drawable.ic_sobrepeso
            )
            else -> Pair(
                getString(R.string.imc_obesidade),
                R.drawable.ic_obesidade
            )
        }
    }
}