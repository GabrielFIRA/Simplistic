package ni.edu.uca.simplistic.datos.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ni.edu.uca.simplistic.datos.modelo.Producto

@Dao
interface DaoProducto {
    // If already exists, do not add
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProducto(producto: Producto)

    @Query("SELECT * FROM Producto ORDER BY nombre ASC")
    fun readAllData(): LiveData<List<Producto>>

    @Query("SELECT * FROM Producto WHERE estado != 3 ORDER BY nombre ASC")
    fun readNonDeletedData(): LiveData<List<Producto>>

    @Query("SELECT * FROM Producto WHERE idProducto = :idProducto")
    fun readProductoById(idProducto: Int): Producto

    @Update
    fun updateProducto(producto: Producto)
}