package ni.edu.uca.simplistic.datos.repositorios

import androidx.lifecycle.LiveData
import ni.edu.uca.simplistic.datos.DAO.DaoProductoCompra
import ni.edu.uca.simplistic.datos.modelo.ProductoCompra

class ProductoCompraRepo(val daoProductoCompra: DaoProductoCompra) {
    val readAllData: LiveData<List<ProductoCompra>> = daoProductoCompra.readAllData()

    suspend fun addProductoCompra(productoCompra: ProductoCompra) {
        daoProductoCompra.addProductoCompra(productoCompra)
    }
}