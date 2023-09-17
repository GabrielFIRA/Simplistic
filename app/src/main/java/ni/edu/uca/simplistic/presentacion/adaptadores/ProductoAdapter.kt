package ni.edu.uca.simplistic.presentacion.adaptadores

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
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
import ni.edu.uca.simplistic.datos.estatico.ProductoCompraGlobal
import ni.edu.uca.simplistic.datos.modelo.Producto
import ni.edu.uca.simplistic.datos.modelo.ProductoCompra
import ni.edu.uca.simplistic.presentacion.GestionarProductoFragment
import ni.edu.uca.simplistic.presentacion.ProductosFragment

class ProductoAdapter(val fragmentActivity: FragmentActivity) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {
    private var listaProductos = emptyList<Producto>()

    inner class ProductoViewHolder(origin: View) : RecyclerView.ViewHolder(origin) {
        private val clProducto = origin.findViewById<ConstraintLayout>(R.id.cl_rv_Productos)
        private val nombreProducto = origin.findViewById<TextView>(R.id.tituloVerProducto)
        private val marca = origin.findViewById<TextView>(R.id.marcaVerProducto)
        private val precio = origin.findViewById<TextView>(R.id.precioUnidadVerProducto)

        private val reducirCantidad = origin.findViewById<ConstraintLayout>(R.id.reducirCantidad)
        private val incrementarCantidad =
            origin.findViewById<ConstraintLayout>(R.id.incrementarCantidad)
        private val cantidad = origin.findViewById<EditText>(R.id.etCantidad)

        @SuppressLint("SetTextI18n")
        fun load(producto: Producto) {
            nombreProducto.text = producto.nombre.uppercase()
            marca.text = producto.marca
            precio.text = "${producto.precio} $ / ${producto.unidad}"

            Log.wtf("UNIDAD: ", producto.unidad)
            if (producto.unidad == "u") {
                cantidad.hint = "0 ${producto.unidad}"
                cantidad.inputType = InputType.TYPE_CLASS_NUMBER
            } else {
                cantidad.hint = "0.0 ${producto.unidad}"
                cantidad.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
            }

            clProducto.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("Producto", producto)
                val gestionarProducto = GestionarProductoFragment()
                gestionarProducto.arguments = bundle

                val fragmentManager = fragmentActivity.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainerView, gestionarProducto)
                fragmentTransaction.commit()
            }
            if(ProductoCompraGlobal.productoCompraList.size != 0) {
                for (li in ProductoCompraGlobal.productoCompraList) {
                    if(li.idProducto == producto.idProducto) {
                        if(producto.unidad == "u") {
                            cantidad.setText("${li.cantidad.toInt()}")
                        }else{
                            cantidad.setText("${li.cantidad}")
                        }
                    }
                }
            }
            // HORRIBLY NESTED x(
            incrementarCantidad.setOnClickListener {
                //validar que sea U
                if (producto.unidad == "u") {
                    val cantidadTemp: Int
                    // Si esta vacio y no es 0 significa que ya existe, si se reduce a 0 deberia eliminarse
                    if (cantidad.text.toString() != "") {
                        if(cantidad.text.toString().toInt() != 0){
                            cantidadTemp = cantidad.text.toString().toInt()
                            cantidad.setText("${cantidadTemp + 1}")
                            for(li in ProductoCompraGlobal.productoCompraList) {
                                if(li.idProducto == producto.idProducto) {
                                    li.cantidad = cantidadTemp + 1f
                                }
                            }
                        }
                    }
                    // Si esta vacio, al presionar el boton en int el resultado siempre sera 1, y se creara nuevo registro temp
                    else {
                        cantidad.setText("1")
                        val productoCompra = ProductoCompra(
                            0,
                            1f,
                            0,
                            producto.idProducto
                        )
                        // si la lista esta vacia, agregar directamente
                        if(ProductoCompraGlobal.productoCompraList.size == 0) {
                            ProductoCompraGlobal.productoCompraList.add(productoCompra)
                        }
                        // Si no, revisar que el producto no se haya agregado previamente.
                        else{
                            for(li in ProductoCompraGlobal.productoCompraList){
                                if(li.idProducto != producto.idProducto){
                                    ProductoCompraGlobal.productoCompraList.add(productoCompra)
                                }
                            }
                        }
                    }
                }
                // En caso de no ser U
                else {
                    val cantidadTemp: Float
                    if (cantidad.text.toString() != "") {
                        cantidadTemp = cantidad.text.toString().toFloat()
                        cantidad.setText("${cantidadTemp + 0.1}")
                        for(li in ProductoCompraGlobal.productoCompraList) {
                            if(li.idProducto == producto.idProducto) {
                                li.cantidad = cantidadTemp + 1f
                            }
                        }
                    } else {
                        cantidad.setText("0.1")
                        for(li in ProductoCompraGlobal.productoCompraList){
                            if(li.idProducto != producto.idProducto){
                                val productoCompra = ProductoCompra(
                                    0,
                                    0.1f,
                                    0,
                                    producto.idProducto
                                )
                                ProductoCompraGlobal.productoCompraList.add(productoCompra)
                            }
                        }
                    }
                }
            }
            reducirCantidad.setOnClickListener {
                if (producto.unidad == "u") {
                    val cantidadTemp: Int
                    if (cantidad.text.toString() != "" && cantidad.text.toString().toInt() -1 > 0) {
                        cantidadTemp = cantidad.text.toString().toInt()
                        cantidad.setText("${cantidadTemp - 1}")
                        for(li in ProductoCompraGlobal.productoCompraList) {
                            if(li.idProducto == producto.idProducto) {
                                li.cantidad = cantidadTemp - 1f
                            }
                        }
                    } else {
                        cantidad.setText("")
                        for(li in ProductoCompraGlobal.productoCompraList) {
                            if(li.idProducto == producto.idProducto) {
                                ProductoCompraGlobal.productoCompraList.remove(li)
                            }
                        }
                    }
                } else {
                    val cantidadTemp: Float
                    if (cantidad.text.toString() != "" && ((cantidad.text.toString()
                            .toFloat() - 0.1f) > 0f)
                    ) {
                        cantidadTemp = cantidad.text.toString().toFloat()
                        cantidad.setText("${cantidadTemp - 0.1f}")
                        for (li in ProductoCompraGlobal.productoCompraList) {
                            if(li.idProducto == producto.idProducto) {
                                li.cantidad = cantidadTemp - 0.1f
                            }
                        }
                    } else {
                        cantidad.setText("")
                        for(li in ProductoCompraGlobal.productoCompraList) {
                            if(li.idProducto == producto.idProducto) {
                                ProductoCompraGlobal.productoCompraList.remove(li)
                            }
                        }
                    }
                }
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
    fun setData(lista: List<Producto>) {
        listaProductos = lista
        notifyDataSetChanged()
    }
}