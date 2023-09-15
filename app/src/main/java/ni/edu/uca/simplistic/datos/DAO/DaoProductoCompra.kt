package ni.edu.uca.simplistic.datos.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ni.edu.uca.simplistic.datos.modelo.ProductoCompra

@Dao
interface DaoProductoCompra {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProductoCompra(productoCompra: ProductoCompra)

    @Query("SELECT * FROM ProductoCompra ORDER BY idProdutoCompra ASC")
    fun readAllData(): LiveData<List<ProductoCompra>>
}