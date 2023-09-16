package ni.edu.uca.simplistic.datos.VistaModelo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ni.edu.uca.simplistic.datos.BD
import ni.edu.uca.simplistic.datos.modelo.Producto
import ni.edu.uca.simplistic.datos.repositorios.ProductoRepo

class ProductoVM(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Producto>>
    private val repository: ProductoRepo

    init {
        val daoProducto = BD.getDataBase(application).daoProducto()
        repository = ProductoRepo(daoProducto)
        readAllData = repository.readAllData
    }

    fun addProducto(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProducto(producto)
        }
    }

    fun updateProducto(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProducto(producto)
        }
    }
}