package br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus.databinding.ActivityPesoIdealBinding
import kotlin.math.abs

class PesoIdealActivity : AppCompatActivity() {

    private val binding: ActivityPesoIdealBinding by lazy {
        ActivityPesoIdealBinding.inflate(layoutInflater)
    }

    private lateinit var dadosPessoais: DadosPessoais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.ideal_titulo)

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constantes.EXTRA_DADOS_PESSOAIS, DadosPessoais::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Constantes.EXTRA_DADOS_PESSOAIS)
        } ?: return

        calcularEExibirPesoIdeal()

        binding.btnVoltarInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    private fun calcularEExibirPesoIdeal() {
        val alturaEmMetros = dadosPessoais.altura / 100.0

        val pesoIdeal = 22 * (alturaEmMetros * alturaEmMetros)
        dadosPessoais.pesoIdeal = pesoIdeal

        val pesoAtual = dadosPessoais.peso
        val diferenca = pesoAtual - pesoIdeal

        binding.tvPesoIdeal.text = getString(R.string.ideal_resultado, pesoIdeal)

        val mensagemDiferenca = when {
            diferenca > 1 -> getString(R.string.ideal_diferenca_perder, diferenca)
            diferenca < -1 -> getString(R.string.ideal_diferenca_ganhar, abs(diferenca))
            else -> getString(R.string.ideal_diferenca_ok)
        }
        binding.tvDiferencaPeso.text = mensagemDiferenca
    }
}