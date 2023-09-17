package ni.edu.uca.simplistic.presentacion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.simplistic.databinding.FragmentListaBinding
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoCompraVM
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoVM
import ni.edu.uca.simplistic.datos.estatico.ProductoCompraGlobal
import ni.edu.uca.simplistic.presentacion.adaptadores.ListaAdapter

class ListaFragment : Fragment() {
    private lateinit var fbinding: FragmentListaBinding
    private lateinit var productoCompraVM: ProductoCompraVM
    private lateinit var productoVM: ProductoVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentListaBinding.inflate(layoutInflater)
        productoCompraVM = ViewModelProvider(this)[ProductoCompraVM::class.java]
        productoVM = ViewModelProvider(this)[ProductoVM::class.java]
        initialize()
        return fbinding.root
    }

    private fun initialize() {
        fbinding.fabSiguiente.setOnClickListener {
            if (ProductoCompraGlobal.productoCompraList.isNotEmpty()) {
                val intent = Intent(requireActivity(), ConfirmarFacturaActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Tiene que agregar productos antes de facturar!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        eliminarProductosVacios()
        loadRV()
    }

    private fun eliminarProductosVacios() {
        for(li in ProductoCompraGlobal.productoCompraList) {
            if(li.cantidad < 0.01) {
                ProductoCompraGlobal.productoCompraList.remove(li)
            }
        }
    }

    private fun loadRV() {
        val adapter = ListaAdapter(productoVM)
        val recycler = fbinding.recyclerListaProductos
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
    }
}