package uz.otamurod.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.joda.time.format.DateTimeFormat
import uz.otamurod.presentation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}