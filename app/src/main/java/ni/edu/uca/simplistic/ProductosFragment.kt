package ni.edu.uca.simplistic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import ni.edu.uca.simplistic.databinding.FragmentProductosBinding


class ProductosFragment : Fragment() {
    private lateinit var fbinding: FragmentProductosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentProductosBinding.inflate(layoutInflater)
        initialize()
        return fbinding.root
    }

    private fun initialize() {
        fbinding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.acProducto_CrearProducto)
        }
    }


}