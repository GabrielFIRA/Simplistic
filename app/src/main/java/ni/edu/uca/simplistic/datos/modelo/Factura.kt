package ni.edu.uca.simplistic.datos.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Factura")
data class Factura (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idFactura")
    var idFactura: Int,
    @ColumnInfo(name = "fechaDeCompra")
    var fechaDeCompra: String
)