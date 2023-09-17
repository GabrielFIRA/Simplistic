package ni.edu.uca.simplistic.presentacion.adaptadores

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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

class ConfirmarFacturaAdapter(val productoVM: ProductoVM) :
    RecyclerView.Adapter<ConfirmarFacturaAdapter.ConfirmarFacturaHolder>() {

    inner class ConfirmarFacturaHolder(origin: View) : RecyclerView.ViewHolder(origin) {
        private val nombreProducto = origin.findViewById<TextView>(R.id.tvNombreProductoConfirmarFactura)
        private val precio = origin.findViewById<TextView>(R.id.tvPrecioProductosConfirmarFactura)
        fun load(productoCompra: ProductoCompra) {
            productoVM.readProductoById(object: MainListener{
                @SuppressLint("SetTextI18n")
                override fun onSuccess(any: Any) {
                    val producto = any as Producto
                    GlobalScope.launch(Dispatchers.Main) {
                        nombreProducto.text = producto.nombre +
                                " x " +
                                productoCompra.cantidad.toString() +
                                producto.unidad.uppercase()
                        val precioTemp =
                            DecimalFormat("0.00").format(
                                (producto.precio * productoCompra.cantidad).toDouble()
                            )
                        precio.text = "${precioTemp} $"
                    }
                }

                override fun onFailure() {
                    Log.wtf("ConfirmarFacturaAdapter", "Error al obtener producto por id")
                }

            }, productoCompra.idProducto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmarFacturaHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ConfirmarFacturaHolder(
            layoutInflater.inflate(
                R.layout.rv_productos_confirmar_factura,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = ProductoCompraGlobal.productoCompraList.size

    override fun onBindViewHolder(holder: ConfirmarFacturaHolder, position: Int) {
        val productoCompra = ProductoCompraGlobal.productoCompraList[position]
        holder.load(productoCompra)
    }

}