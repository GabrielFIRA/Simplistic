package ni.edu.uca.simplistic.datos.repositorios

import androidx.lifecycle.LiveData
import ni.edu.uca.simplistic.datos.DAO.DaoFactura
import ni.edu.uca.simplistic.datos.modelo.Factura
import ni.edu.uca.simplistic.datos.utils.MainListener
import java.lang.Exception

class FacturaRepo(private val daoFactura: DaoFactura) {
    val readAllData: LiveData<List<Factura>> = daoFactura.readAllData()

    suspend fun addFactura(factura: Factura){
        daoFactura.addFactura(factura)
    }

    fun readLastFactura(mainListener: MainListener) {
        try {
            mainListener.onSuccess(daoFactura.readLastFactura()[0])
        }catch (e: Exception) {
            mainListener.onFailure()
            e.printStackTrace()
        }
    }
}