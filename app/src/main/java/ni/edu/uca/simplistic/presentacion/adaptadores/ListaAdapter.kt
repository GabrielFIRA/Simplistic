package ni.edu.uca.simplistic.presentacion.adaptadores

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ni.edu.uca.simplistic.R
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoVM
import ni.edu.uca.simplistic.datos.estatico.ProductoCompraGlobal
import ni.edu.uca.simplistic.datos.modelo.Producto
import ni.edu.uca.simplistic.datos.modelo.ProductoCompra
import ni.edu.uca.simplistic.datos.utils.MainListener
import java.text.DecimalFormat

class ListaAdapter(val productoVM: ProductoVM) :
    RecyclerView.Adapter<ListaAdapter.ListaAdapterViewHolder>() {
    private lateinit var productoTemp: Producto

    inner class ListaAdapterViewHolder(origin: View) : RecyclerView.ViewHolder(origin) {
        private val producto = origin.findViewById<TextView>(R.id.tvTituloVerLista)
        private val precioTotal = origin.findViewById<TextView>(R.id.tvPrecioVerLista)
        private val btnEliminarProducto =
            origin.findViewById<MaterialButton>(R.id.btnEliminarProductoLista)

        // mother of christ
        @SuppressLint("SetTextI18n")
        fun load(productoCompra: ProductoCompra, position: Int) {
            productoVM.readProductoById(object : MainListener {
                override fun onSuccess(any: Any) {
                    GlobalScope.launch(Dispatchers.Main) {
                        productoTemp = any as Producto
                        producto.text =
                            productoTemp.nombre.uppercase() + " x " +
                                    productoCompra.cantidad.toString() +
                                    productoTemp.unidad.uppercase()

                        val precio =
                            DecimalFormat("0.00").format((productoTemp.precio * productoCompra.cantidad).toDouble())
                        precioTotal.text = "$precio $"
                    }
                }

                override fun onFailure() {
                    Log.wtf("Lista Adapter", "ERROR AL BUSCAR PRODUCTO POR ID")
                }
            }, productoCompra.idProducto)

            btnEliminarProducto.setOnClickListener {
                for (li in ProductoCompraGlobal.productoCompraList) {
                    if (li.idProductoCompra == productoCompra.idProductoCompra) {
                        notifyItemRemoved(position)
                        ProductoCompraGlobal.productoCompraList.remove(li)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListaAdapterViewHolder(layoutInflater.inflate(R.layout.rv_lista, parent, false))
    }

    override fun getItemCount() = ProductoCompraGlobal.productoCompraList.size

    override fun onBindViewHolder(holder: ListaAdapterViewHolder, position: Int) {
        val productoCompra = ProductoCompraGlobal.productoCompraList[position]
        holder.load(productoCompra, position)
    }

}