package ni.edu.uca.simplistic.datos.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Producto")
data class Producto (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idProducto")
    var idProducto: Int,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "precio")
    var precio: Float,
    @ColumnInfo(name = "marca")
    var marca: String,
    @ColumnInfo(name = "unidad")
    var unidad: String,
    @ColumnInfo(name = "estado")
    var estado: Int // 0 = inactivo/eliminado, 1 = activo
)