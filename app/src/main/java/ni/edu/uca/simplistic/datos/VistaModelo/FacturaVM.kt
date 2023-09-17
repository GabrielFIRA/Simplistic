package ni.edu.uca.simplistic.datos.VistaModelo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ni.edu.uca.simplistic.datos.BD
import ni.edu.uca.simplistic.datos.modelo.Factura
import ni.edu.uca.simplistic.datos.repositorios.FacturaRepo
import ni.edu.uca.simplistic.datos.utils.MainListener

class FacturaVM(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Factura>>
    private val repository: FacturaRepo

    init {
        val daoFactura = BD.getDataBase(application).daoFactura()
        repository = FacturaRepo(daoFactura)
        readAllData = repository.readAllData
    }

    fun addFactura(factura: Factura) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFactura(factura)
        }
    }

    fun readLastFactura(mainListener: MainListener) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.readLastFactura(mainListener)
        }
    }
}