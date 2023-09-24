package ni.edu.uca.simplistic.datos.repositorios

import androidx.lifecycle.LiveData
import ni.edu.uca.simplistic.datos.DAO.DaoProducto
import ni.edu.uca.simplistic.datos.modelo.Producto
import ni.edu.uca.simplistic.datos.utils.MainListener
import java.lang.Exception

class ProductoRepo(val daoProducto: DaoProducto) {
    val readAllData: LiveData<List<Producto>> = daoProducto.readAllData()
    val readNonDeletedData: LiveData<List<Producto>> = daoProducto.readNonDeletedData()

    suspend fun readProductoById(mainListener: MainListener, idProducto: Int) {
        try {
            mainListener.onSuccess(daoProducto.readProductoById(idProducto))
        }catch (e: Exception){
            mainListener.onFailure()
            e.printStackTrace()
        }
    }

    suspend fun addProducto(producto: Producto) {
        daoProducto.addProducto(producto)
    }

    suspend fun updateProducto(producto: Producto) {
        daoProducto.updateProducto(producto)
    }
}