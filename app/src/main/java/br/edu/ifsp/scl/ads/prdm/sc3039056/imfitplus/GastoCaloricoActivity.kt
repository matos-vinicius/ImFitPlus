package br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus.databinding.ActivityGastoCaloricoBinding

class GastoCaloricoActivity : AppCompatActivity() {

    private val binding: ActivityGastoCaloricoBinding by lazy {
        ActivityGastoCaloricoBinding.inflate(layoutInflater)
    }
    private lateinit var dadosPessoais: DadosPessoais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.gasto_titulo)

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constantes.EXTRA_DADOS_PESSOAIS, DadosPessoais::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Constantes.EXTRA_DADOS_PESSOAIS)
        } ?: return

        calcularEExibirGasto()

        binding.btnCalcularPesoIdeal.setOnClickListener {
            val intent = Intent(this, PesoIdealActivity::class.java).apply {
                putExtra(Constantes.EXTRA_DADOS_PESSOAIS, dadosPessoais)
            }
            startActivity(intent)
        }

        binding.btnVoltarGasto.setOnClickListener {
            finish()
        }
    }

    private fun calcularEExibirGasto() {
        val tmb = calcularTmb()
        val fatorAtividade = getFatorAtividade()
        val gastoTotal = tmb * fatorAtividade

        dadosPessoais.tmb = tmb
        dadosPessoais.gastoCalorico = gastoTotal

        binding.tvTmb.text = getString(R.string.gasto_tmb, tmb)
        binding.tvGastoTotal.text = getString(R.string.gasto_total, gastoTotal)
    }

    private fun calcularTmb(): Double {
        val p = dadosPessoais.peso
        val a = dadosPessoais.altura
        val i = dadosPessoais.idade.toDouble()

        return if (dadosPessoais.sexo == getString(R.string.dados_masculino)) {
             66 + (13.7 * p) + (5 * a) - (6.8 * i)
        } else {
             655 + (9.6 * p) + (1.8 * a) - (4.7 * i)
        }
    }

    private fun getFatorAtividade(): Double {
        return when (dadosPessoais.nivelAtividade) {
            "Sedentário" -> 1.2
            "Leve" -> 1.375
            "Moderado" -> 1.55
            "Intenso" -> 1.725
            else -> 1.0
        }
    }
}