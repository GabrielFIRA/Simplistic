package ni.edu.uca.simplistic.datos.VistaModelo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ni.edu.uca.simplistic.datos.BD
import ni.edu.uca.simplistic.datos.modelo.ProductoCompra
import ni.edu.uca.simplistic.datos.repositorios.ProductoCompraRepo

class ProductoCompraVM(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<ProductoCompra>>
    private val repository: ProductoCompraRepo

    init {
        val productoCompraDao = BD.getDataBase(application).daoProductoCompra()
        repository = ProductoCompraRepo(productoCompraDao)
        readAllData = repository.readAllData
    }

    fun addProductoCompra(productoCompra: ProductoCompra) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProductoCompra(productoCompra)
        }
    }
}