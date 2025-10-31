package br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus.databinding.ActivityDadosPessoaisBinding

class DadosPessoaisActivity : AppCompatActivity() {

    private val binding: ActivityDadosPessoaisBinding by lazy {
        ActivityDadosPessoaisBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.dados_pessoais_titulo)

        binding.btnCalcularImc.setOnClickListener {
            if (validarCampos()) {
                val dados = coletarDados()
                dados.imc = calcularImc(dados.peso, dados.altura)

                val intent = Intent(this, ResultadoImcActivity::class.java).apply {
                    putExtra(Constantes.EXTRA_DADOS_PESSOAIS, dados)
                }
                startActivity(intent)
            }
        }
    }

    private fun validarCampos(): Boolean {
        var valido = true

        if (binding.etNome.text.isNullOrBlank()) {
            binding.etNome.error = "Campo obrigatório"
            valido = false
        }
        if (binding.etIdade.text.isNullOrBlank()) {
            binding.etIdade.error = "Campo obrigatório"
            valido = false
        }
        if (binding.rgSexo.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Selecione o sexo", Toast.LENGTH_SHORT).show()
            valido = false
        }
        if (binding.etAltura.text.isNullOrBlank() || binding.etAltura.text.toString().toDoubleOrNull() == 0.0) {
            binding.etAltura.error = "Altura inválida"
            valido = false
        }
        if (binding.etPeso.text.isNullOrBlank() || binding.etPeso.text.toString().toDoubleOrNull() == 0.0) {
            binding.etPeso.error = "Peso inválido"
            valido = false
        }
        if (binding.spNivelAtividade.selectedItemPosition == 0) {
            Toast.makeText(this, getString(R.string.erro_spinner), Toast.LENGTH_SHORT).show()
            valido = false
        }

        if (!valido && binding.etIdade.text.isNullOrBlank()) {
            Toast.makeText(this, getString(R.string.erro_validacao), Toast.LENGTH_LONG).show()
        }
        return valido
    }

    private fun coletarDados(): DadosPessoais {
        val nome = binding.etNome.text.toString()
        val idade = binding.etIdade.text.toString().toInt()
        val sexo = findViewById<RadioButton>(binding.rgSexo.checkedRadioButtonId).text.toString()
        val altura = binding.etAltura.text.toString().toDouble()
        val peso = binding.etPeso.text.toString().toDouble()
        val nivelAtividade = binding.spNivelAtividade.selectedItem.toString()

        return DadosPessoais(nome, idade, sexo, altura, peso, nivelAtividade)
    }
    private fun calcularImc(peso: Double, alturaCm: Double): Double {
        val alturaEmMetros = alturaCm / 100.0
        return peso / (alturaEmMetros * alturaEmMetros)
    }
}