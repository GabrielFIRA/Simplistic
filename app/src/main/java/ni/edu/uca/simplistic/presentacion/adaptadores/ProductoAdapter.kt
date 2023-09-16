package ni.edu.uca.simplistic.presentacion.adaptadores

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.simplistic.R
import ni.edu.uca.simplistic.datos.modelo.Producto
import ni.edu.uca.simplistic.presentacion.GestionarProductoFragment
import ni.edu.uca.simplistic.presentacion.ProductosFragment

class ProductoAdapter(val fragmentActivity: FragmentActivity): RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {
    private var listaProductos = emptyList<Producto>()
    inner class ProductoViewHolder(origin: View): RecyclerView.ViewHolder(origin)
    {
        private val clProducto = origin.findViewById<ConstraintLayout>(R.id.cl_rv_Productos)
        private val nombreProducto = origin.findViewById<TextView>(R.id.tituloVerProducto)
        private val marca = origin.findViewById<TextView>(R.id.marcaVerProducto)
        private val precio = origin.findViewById<TextView>(R.id.precioUnidadVerProducto)

        private val reducirCantidad = origin.findViewById<ConstraintLayout>(R.id.reducirCantidad)
        private val incrementarCantidad = origin.findViewById<ConstraintLayout>(R.id.incrementarCantidad)
        private val cantidad = origin.findViewById<EditText>(R.id.etCantidad)

        @SuppressLint("SetTextI18n")
        fun load(producto: Producto) {
            nombreProducto.text = producto.nombre
            marca.text = producto.marca
            precio.text = "${producto.precio} $ / ${producto.unidad}"

            Log.wtf("UNIDAD: ", producto.unidad)
            if (producto.unidad == "u"){
                cantidad.setText("0 ${producto.unidad}")
            }
            else {
                cantidad.setText("0.0 ${producto.unidad}")
            }

            clProducto.setOnClickListener{
                val bundle = Bundle()
                bundle.putParcelable("Producto", producto)
                val gestionarProducto = GestionarProductoFragment()
                gestionarProducto.arguments = bundle

                val fragmentManager = fragmentActivity.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainerView, gestionarProducto)
                fragmentTransaction.commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(R.layout.rv_productos, parent, false))
    }

    override fun getItemCount() = listaProductos.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.load(producto)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(lista :List<Producto>) {
        listaProductos = lista
        notifyDataSetChanged()
    }
}