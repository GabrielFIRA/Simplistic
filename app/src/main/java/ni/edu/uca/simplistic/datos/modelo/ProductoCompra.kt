package ni.edu.uca.simplistic.datos.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ProductoCompra", foreignKeys = arrayOf(
        ForeignKey(
            entity = Producto::class,
            parentColumns = arrayOf("idProducto"),
            childColumns = arrayOf("idProducto")
        ),
        ForeignKey(
            entity = Factura::class,
            parentColumns = arrayOf("idFactura"),
            childColumns = arrayOf("idFactura")
        )
    )
)
data class ProductoCompra(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idProdutoCompra")
    var idProductoCompra: Int,
    @ColumnInfo(name = "cantidad")
    var cantidad: Float,

    // FKs
    @ColumnInfo(name = "idFactura")
    var idFactura: Int,
    @ColumnInfo(name = "idProducto")
    var idProducto: Int
)