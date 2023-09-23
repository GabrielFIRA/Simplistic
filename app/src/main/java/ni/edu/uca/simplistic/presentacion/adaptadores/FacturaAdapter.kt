package ni.edu.uca.simplistic.presentacion.adaptadores

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.simplistic.R
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoCompraVM
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoVM
import ni.edu.uca.simplistic.datos.modelo.Factura
import ni.edu.uca.simplistic.datos.modelo.Producto
import ni.edu.uca.simplistic.datos.modelo.ProductoCompra
import java.text.DecimalFormat

class FacturaAdapter(
    val productoVM: ProductoVM,
    val productoCompraVM: ProductoCompraVM,
    val fragment: Fragment
) : RecyclerView.Adapter<FacturaAdapter.FacturaViewHolder>() {
    private var facturaList = emptyList<Factura>()

    inner class FacturaViewHolder(private val origin: View) : RecyclerView.ViewHolder(origin) {
        private val fecha = origin.findViewById<TextView>(R.id.tvFechaFactura)
        private val total = origin.findViewById<TextView>(R.id.tvTotalFactura)
        private val clfactura = origin.findViewById<ConstraintLayout>(R.id.recyclerFactura)
        private val rvProductosComprados = origin.findViewById<RecyclerView>(R.id.rvProductosComprados)
        private val clIcono = origin.findViewById<ConstraintLayout>(R.id.mostrarProductosComprados)
        private var isOpen = false

        @SuppressLint("SetTextI18n")
        fun load(factura: Factura) {
            clfactura.setOnClickListener { openClose() }
            fecha.text = factura.fechaDeCompra
            val productosCompra = ArrayList<ProductoCompra>()
            productoCompraVM.readAllData.observe(fragment.viewLifecycleOwner, Observer {pc ->
                for (li in pc) {
                    if (li.idFactura == factura.idFactura) {
                        productosCompra.add(li)
                    }
                }
                var precioTotal = 0f
                val productos = ArrayList<Producto>()
                productoVM.readAllData.observe(fragment.viewLifecycleOwner, Observer { prod ->
                    for (li in productosCompra) {
                        val list = prod.find { it.idProducto == li.idProducto }
                        if (list != null) {
                            productos.add(list)
                            precioTotal += list.precio * li.cantidad
                        }
                        Log.wtf("FACTURA: ", "${li.idFactura}")
                    }
                    total.text = "${DecimalFormat("0.00").format((precioTotal).toDouble())} $"
                    loadRV2(productosCompra, productos)
                })
            })
        }

        private fun openClose() {
            isOpen = !isOpen
            if(isOpen){
                rvProductosComprados.visibility = View.VISIBLE
                clIcono.rotation = 0f
            } else{
                rvProductosComprados.visibility = View.GONE
                clIcono.rotation = 180f
            }
        }

        private fun loadRV2(productosCompra: List<ProductoCompra>, productos: List<Producto>) {
            val adapter = ProductoFacturaAdapter()
            val recycler = origin.findViewById<RecyclerView>(R.id.rvProductosComprados)
            recycler.layoutManager = LinearLayoutManager(fragment.requireContext())
            recycler.setHasFixedSize(true)
            recycler.adapter = adapter
            adapter.setData(productosCompra, productos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FacturaViewHolder(layoutInflater.inflate(R.layout.rv_facturas, parent, false))
    }

    override fun getItemCount() = facturaList.size

    override fun onBindViewHolder(holder: FacturaViewHolder, position: Int) {
        val factura = facturaList[position]
        holder.load(factura)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(facturaList: List<Factura>) {
        this.facturaList = facturaList
        notifyDataSetChanged()
    }
}