package ni.edu.uca.simplistic.datos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ni.edu.uca.simplistic.datos.DAO.DaoFactura
import ni.edu.uca.simplistic.datos.DAO.DaoProducto
import ni.edu.uca.simplistic.datos.DAO.DaoProductoCompra
import ni.edu.uca.simplistic.datos.modelo.Factura
import ni.edu.uca.simplistic.datos.modelo.Producto
import ni.edu.uca.simplistic.datos.modelo.ProductoCompra

@Database(
    entities = [Producto::class, ProductoCompra::class, Factura::class],
    version = 2,
    exportSchema = false
)
abstract class BD : RoomDatabase() {
    abstract fun daoProducto(): DaoProducto
    abstract fun daoProductoCompra(): DaoProductoCompra
    abstract fun daoFactura(): DaoFactura

    companion object {
        //Singleton
        @Volatile
        private var INSTANCE: BD? = null
        fun getDataBase(context: Context): BD {
            val tempInstace = INSTANCE
            if(tempInstace != null) {
                return tempInstace
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BD::class.java,
                    "BD_Simplistic2"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}