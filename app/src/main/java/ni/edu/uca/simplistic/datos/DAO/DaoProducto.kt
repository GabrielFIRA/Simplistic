package ni.edu.uca.simplistic.datos.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ni.edu.uca.simplistic.datos.modelo.Producto

@Dao
interface DaoProducto {
    // If already exists, do not add
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProducto(producto: Producto)

    @Query("SELECT * FROM Producto ORDER BY nombre ASC")
    fun readAllData(): LiveData<List<Producto>>
}