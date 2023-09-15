package ni.edu.uca.simplistic.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ni.edu.uca.simplistic.databinding.FragmentGestionarProductoBinding

class GestionarProductoFragment : Fragment() {
    private lateinit var fbinding: FragmentGestionarProductoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentGestionarProductoBinding.inflate(layoutInflater)
        return fbinding.root
    }

}