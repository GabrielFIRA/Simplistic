package ni.edu.uca.simplistic.datos.repositorios

import androidx.lifecycle.LiveData
import ni.edu.uca.simplistic.datos.DAO.DaoProducto
import ni.edu.uca.simplistic.datos.modelo.Producto

class ProductoRepo(val daoProducto: DaoProducto) {
    val readAllData: LiveData<List<Producto>> = daoProducto.readAllData()

    suspend fun addProducto(producto: Producto) {
        daoProducto.addProducto(producto)
    }

    suspend fun updateProducto(producto: Producto) {
        daoProducto.updateProducto(producto)
    }
}