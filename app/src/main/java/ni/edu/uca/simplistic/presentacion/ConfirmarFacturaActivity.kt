package ni.edu.uca.simplistic.presentacion

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ni.edu.uca.simplistic.databinding.ActivityConfirmarFacturaBinding
import ni.edu.uca.simplistic.datos.VistaModelo.FacturaVM
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoCompraVM
import ni.edu.uca.simplistic.datos.VistaModelo.ProductoVM
import ni.edu.uca.simplistic.datos.estatico.ProductoCompraGlobal
import ni.edu.uca.simplistic.datos.modelo.Factura
import ni.edu.uca.simplistic.datos.modelo.Producto
import ni.edu.uca.simplistic.datos.utils.MainListener
import ni.edu.uca.simplistic.presentacion.adaptadores.ConfirmarFacturaAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ConfirmarFacturaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmarFacturaBinding
    private lateinit var productoVM: ProductoVM
    private lateinit var productoCompraVM: ProductoCompraVM
    private lateinit var facturaVM: FacturaVM
    private lateinit var fechaActual: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmarFacturaBinding.inflate(layoutInflater)
        productoVM = ViewModelProvider(this)[ProductoVM::class.java]
        productoCompraVM = ViewModelProvider(this)[ProductoCompraVM::class.java]
        facturaVM = ViewModelProvider(this)[FacturaVM::class.java]

        val fecha = Date()
        val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        fechaActual = dateFormat.format(fecha)
        initialize()
        setContentView(binding.root)
    }

    @SuppressLint("SetTextI18n")
    private fun initialize() {
        var productoList = emptyList<Producto>()
        productoVM.readAllData.observe(this, Observer { productos ->
            productoList = productos
            var total: Float = 0.0f
            for(prodComp in ProductoCompraGlobal.productoCompraList){
                val prod = productoList.find { it.idProducto == prodComp.idProducto}
                Log.wtf("TOTAL: ", "${productoList.size}")
                total += (prod!!.precio * prodComp.cantidad)
            }
            binding.tvTotalConfirmarFactura.text = "$total$"
        })

        binding.tvFechaConfirmarFactura.text = fechaActual
        binding.btnGuardarFactura.setOnClickListener {
            guardarFactura()
        }
        loadRV()
    }

    private fun loadRV() {
        val recycler = binding.rvProductosConfirmarFactura
        recycler.adapter = ConfirmarFacturaAdapter(productoVM)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
    }

    private fun guardarFactura() {
        val context = this
        val factura = Factura(0, fechaActual)
        facturaVM.addFactura(factura)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            facturaVM.readLastFactura(object: MainListener{
                override fun onSuccess(any: Any) {
                    val facturaFinal = any as Factura
                    for (li in ProductoCompraGlobal.productoCompraList) {
                        // agregar a bd
                        li.idFactura = facturaFinal.idFactura
                        productoCompraVM.addProductoCompra(li)
                    }
                    // Eliminar arreglo estatico
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000)
                        ProductoCompraGlobal.productoCompraList.removeAll(ProductoCompraGlobal.productoCompraList.toSet())
                    }
                }

                override fun onFailure() {
                    Log.wtf("ConfirmarFacturaActivity", "Error al obtener factura final")
                }
            })
        }
        Toast.makeText(context, "Factura guardada exitosamente", Toast.LENGTH_SHORT).show()
    }
}