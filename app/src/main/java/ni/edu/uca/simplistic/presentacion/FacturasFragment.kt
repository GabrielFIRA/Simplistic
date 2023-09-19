package ni.edu.uca.simplistic.presentacion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.simplistic.databinding.FragmentFacturasBinding
import ni.edu.uca.simplistic.datos.VistaModelo.FacturaVM
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoCompraVM
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoVM
import ni.edu.uca.simplistic.presentacion.adaptadores.FacturaAdapter

class FacturasFragment : Fragment() {
    private lateinit var fbinding: FragmentFacturasBinding
    private lateinit var facturaVM: FacturaVM
    private lateinit var productoVM: ProductoVM
    private lateinit var productoCompraVM: ProductoCompraVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentFacturasBinding.inflate(layoutInflater)
        facturaVM = ViewModelProvider(this)[FacturaVM::class.java]
        productoVM = ViewModelProvider(this)[ProductoVM::class.java]
        productoCompraVM = ViewModelProvider(this)[ProductoCompraVM::class.java]
        initialize()
        return fbinding.root
    }

    private fun initialize() {
        loadRV()
    }

    private fun loadRV() {
        val adapter = FacturaAdapter(productoVM, productoCompraVM, this)
        val recycler = fbinding.rvFactura
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter

        facturaVM.readAllData.observe(viewLifecycleOwner, Observer {facturas ->
            for(li in facturas) {
                Log.wtf("FACTURA SIZE", facturas.size.toString())
            }
            adapter.setData(facturas)
        })
    }
}