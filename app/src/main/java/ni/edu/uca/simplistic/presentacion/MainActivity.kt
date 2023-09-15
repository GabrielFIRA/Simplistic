package ni.edu.uca.simplistic.presentacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ni.edu.uca.simplistic.R
import ni.edu.uca.simplistic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.productoMenuItem -> replaceFragments(ProductosFragment())
                R.id.listaMenuItem -> replaceFragments(ListaFragment())
                R.id.facturaMenuItem -> replaceFragments(FacturasFragment())
                else->{}
            }
            true
        }
    }
    private fun replaceFragments(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}