package ni.edu.uca.simplistic.datos.repositorios

import androidx.lifecycle.LiveData
import ni.edu.uca.simplistic.datos.DAO.DaoFactura
import ni.edu.uca.simplistic.datos.modelo.Factura

class FacturaRepo(private val daoFactura: DaoFactura) {
    val readAllData: LiveData<List<Factura>> = daoFactura.readAllData()

    suspend fun addFactura(factura: Factura){
        daoFactura.addFactura(factura)
    }
}