package ni.edu.uca.simplistic.datos.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ni.edu.uca.simplistic.datos.modelo.Factura

@Dao
interface DaoFactura {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFactura(factura: Factura)

    @Query("SELECT * FROM Factura ORDER BY fechaDeCompra DESC")
    fun readAllData(): LiveData<List<Factura>>
}