package ni.edu.uca.simplistic.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.simplistic.R
import ni.edu.uca.simplistic.databinding.FragmentProductosBinding
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoVM
import ni.edu.uca.simplistic.presentacion.adaptadores.ProductoAdapter


class ProductosFragment : Fragment() {
    private lateinit var fbinding: FragmentProductosBinding
    private lateinit var productoVM: ProductoVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentProductosBinding.inflate(layoutInflater)
        productoVM = ViewModelProvider(this)[ProductoVM::class.java]
        initialize()
        return fbinding.root
    }

    private fun initialize() {
        fbinding.floatingActionButton.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView, CrearProductoFragment())
            fragmentTransaction.commit()
        }
        loadRV()
    }

    private fun loadRV() {
        val adapter = ProductoAdapter(requireActivity())
        val recycler = fbinding.rvProductos
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
        productoVM.readNonDeletedData.observe(viewLifecycleOwner, Observer {producto ->
            adapter.setData(producto)
        })
    }


}