package br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039056.imfitplus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.boas_vindas_titulo)

        binding.btnComecar.setOnClickListener {
            val intent = Intent(this, DadosPessoaisActivity::class.java)
            startActivity(intent)
        }
    }
}