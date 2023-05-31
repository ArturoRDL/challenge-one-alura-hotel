package hotel.jdbc.contoller;

import java.sql.Date;
import java.util.List;

import hotel.jdbc.connection.ConeccionDb;
import hotel.jdbc.dao.HuespedDao;
import hotel.jdbc.modelo.Huesped;

public class HuespedController {

	private HuespedDao huespedDao;

    public HuespedController() {
    	var factory = new ConeccionDb();
        this.huespedDao = new HuespedDao(factory.getConexion());
    }

    public List<Huesped> listar() {
        return huespedDao.listar();
    }
    
        public List<Huesped> listarBusqueda(String cadena) {
        return huespedDao.listarBusqueda(cadena);
    }

    public void guardar(Huesped huesped) {
        this.huespedDao.guardar(huesped);
    }

    public int modificar(int idHuesped, int idReserva, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono) {
        return this.huespedDao.modificar(idHuesped, idReserva, nombre, apellido, fechaNacimiento, nacionalidad, telefono);
    }

    public int eliminar(Integer idHuesped) {
        return huespedDao.eliminar(idHuesped);
    }
}
