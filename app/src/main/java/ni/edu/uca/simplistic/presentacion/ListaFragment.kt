package ni.edu.uca.simplistic.presentacion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ni.edu.uca.simplistic.databinding.FragmentListaBinding

class ListaFragment : Fragment() {
    private lateinit var fbinding: FragmentListaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentListaBinding.inflate(layoutInflater)
        initialize()
        return fbinding.root
    }

    private fun initialize() {
        fbinding.fabSiguiente.setOnClickListener {
            val intent = Intent(requireActivity(), ConfirmarFacturaActivity::class.java)
            startActivity(intent)
        }
    }
}