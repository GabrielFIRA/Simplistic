package ni.edu.uca.simplistic.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ni.edu.uca.simplistic.R
import ni.edu.uca.simplistic.databinding.FragmentCrearProductoBinding
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoVM
import ni.edu.uca.simplistic.datos.modelo.Producto

class CrearProductoFragment : Fragment() {
    private lateinit var fbinding: FragmentCrearProductoBinding
    private lateinit var productoVM: ProductoVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentCrearProductoBinding.inflate(layoutInflater)
        productoVM = ViewModelProvider(this)[ProductoVM::class.java]
        initialize()
        return fbinding.root
    }

    private fun initialize() {
        fbinding.btnCrearProducto.setOnClickListener {
            addProducto()
        }
    }

    private fun addProducto() {
        if(inputCheck()){
            Toast.makeText(requireActivity(), "Todos los campos son obligatorios!", Toast.LENGTH_SHORT).show()
            return
        }
        val nombre: String = fbinding.etNombreProductoCrear.text.toString()
        val precio: Float = fbinding.etPrecioProductoCrear.text.toString().toFloat()
        val marca: String = fbinding.etMarcaProductoCrear.text.toString()
        val unidad: String = if(fbinding.rbUnidadCrear.isChecked) "u"
        else fbinding.etTipoProductoCrear.text.toString()

        val producto = Producto(0, nombre, precio, marca, unidad, 1)
        productoVM.addProducto(producto)
        Toast.makeText(requireActivity(), "Producto agregado", Toast.LENGTH_SHORT).show()

        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, ProductosFragment())
        fragmentTransaction.commit()
    }

    private fun inputCheck(): Boolean {
        if(fbinding.etNombreProductoCrear.text.toString() == "" ||
            fbinding.etPrecioProductoCrear.text.toString() == "" ||
            fbinding.etMarcaProductoCrear.text.toString() == "" ||
            (fbinding.rbOtroCrear.isChecked && fbinding.etTipoProductoCrear.text.toString() == "")
        )
        {
            return true
        }
        return false
    }

}