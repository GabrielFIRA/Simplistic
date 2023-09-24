package ni.edu.uca.simplistic.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ni.edu.uca.simplistic.R
import ni.edu.uca.simplistic.databinding.FragmentGestionarProductoBinding
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoVM
import ni.edu.uca.simplistic.datos.modelo.Producto

class GestionarProductoFragment : Fragment() {
    private lateinit var fbinding: FragmentGestionarProductoBinding
    private lateinit var producto: Producto
    private lateinit var productoVM: ProductoVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentGestionarProductoBinding.inflate(layoutInflater)
        producto = arguments?.getParcelable<Producto>("Producto")!!
        productoVM = ViewModelProvider(this)[ProductoVM::class.java]
        initialize()
        return fbinding.root
    }

    private fun initialize() {
        fbinding.btnModificarProducto.setOnClickListener {
            updateProducto()
        }
        fbinding.btnEliminarProducto.setOnClickListener {
            eliminarProducto()
        }
        loadEditText()
    }

    private fun eliminarProducto() {
        producto.estado = 3
        productoVM.updateProducto(producto)
        Toast.makeText(requireActivity(), "Eliminado exitosamente", Toast.LENGTH_SHORT).show()

        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, ProductosFragment())
        fragmentTransaction.commit()
    }

    private fun loadEditText() {
        fbinding.etNombreProductoGestionar.setText(producto.nombre)
        fbinding.etMarcaProductoGestionar.setText(producto.marca)
        fbinding.etPrecioProductoGestionar.setText(producto.precio.toString())
        if(producto.unidad == "u") {
            fbinding.rbUnidadGestionar.isChecked = true
            fbinding.rbOtroGestionar.isChecked = false
        }else {
            fbinding.rbOtroGestionar.isChecked = true
            fbinding.rbUnidadGestionar.isChecked = false
            fbinding.etTipoProductoGestionar.setText(producto.unidad)
        }
    }

    private fun updateProducto() {
        if (inputCheck()) {
            Toast.makeText(
                requireActivity(),
                "Todos los campos son obligatorios!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val nombre = fbinding.etNombreProductoGestionar.text.toString()
        val marca = fbinding.etMarcaProductoGestionar.text.toString()
        val precio = fbinding.etPrecioProductoGestionar.text.toString().toFloat()
        val unidad: String = if (fbinding.rbUnidadGestionar.isChecked) "u"
        else fbinding.etTipoProductoGestionar.text.toString()

        val productoTemp = Producto(producto.idProducto, nombre, precio, marca, unidad, 1)
        productoVM.updateProducto(productoTemp)
        Toast.makeText(requireActivity(), "Guardado exitosamente", Toast.LENGTH_SHORT).show()

        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, ProductosFragment())
        fragmentTransaction.commit()
    }

    private fun inputCheck(): Boolean {
        if (fbinding.etNombreProductoGestionar.text.toString() == "" ||
            fbinding.etPrecioProductoGestionar.text.toString() == "" ||
            fbinding.etMarcaProductoGestionar.text.toString() == "" ||
            (fbinding.rbOtroGestionar.isChecked && fbinding.etTipoProductoGestionar.text.toString() == "")
        ) return true
        return false
    }

}