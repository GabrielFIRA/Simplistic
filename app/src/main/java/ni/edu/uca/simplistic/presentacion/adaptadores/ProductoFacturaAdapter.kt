package ni.edu.uca.simplistic.presentacion.adaptadores

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.simplistic.R
import ni.edu.uca.simplistic.datos.modelo.Producto
import ni.edu.uca.simplistic.datos.modelo.ProductoCompra
import java.text.DecimalFormat

class ProductoFacturaAdapter: RecyclerView.Adapter<ProductoFacturaAdapter.ProductoFacturaHolder>() {
    private var productoCompraList = emptyList<ProductoCompra>()
    private var productoList = emptyList<Producto>()
    inner class ProductoFacturaHolder(val origin: View): RecyclerView.ViewHolder(origin) {
        private val producto = origin.findViewById<TextView>(R.id.tvNombreProductoFactura)
        private val precio = origin.findViewById<TextView>(R.id.tvPrecioProductoFactura)

        @SuppressLint("SetTextI18n")
        fun load(productoCompra: ProductoCompra) {
            val prod = productoList.find { it.idProducto == productoCompra.idProducto }
            if(prod != null) {
                producto.text = prod.nombre +
                        " x " +
                        (if (prod.unidad == "u") productoCompra.cantidad.toInt().toString() else productoCompra.cantidad.toString()) +
                        prod.unidad
                precio.text = "${DecimalFormat("0.00").format((prod.precio * productoCompra.cantidad).toDouble())} $"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoFacturaHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoFacturaHolder(layoutInflater.inflate(R.layout.rv_productos_factura, parent, false))
    }

    override fun getItemCount() = productoCompraList.size

    override fun onBindViewHolder(holder: ProductoFacturaHolder, position: Int) {
        val productoCompra = productoCompraList[position]
        holder.load(productoCompra)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(productoCompraList: List<ProductoCompra>, productoList: List<Producto>) {
        this.productoCompraList = productoCompraList
        this.productoList = productoList
        notifyDataSetChanged()
    }
}