package hotel.jdbc.contoller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.toedter.calendar.JDateChooser;

import hotel.jdbc.connection.ConeccionDb;
import hotel.jdbc.dao.ReservaDao;
import hotel.jdbc.modelo.Reserva;

public class ReservaController {
	
	private final ReservaDao reservaDao;
	private final double valorReserva = 12500;
	
	public ReservaController() {
		var factory = new ConeccionDb();
		this.reservaDao = new ReservaDao(factory.getConexion());
	}
	public List<Reserva> listar() {
        return reservaDao.listar();
    }

    public List<Reserva> listarBusqueda(int idBusqueda) {
        return reservaDao.listarBusqueda(idBusqueda);
    }

    public int guardar(Reserva reserva) {
        return this.reservaDao.guardar(reserva);
    }

    public int modificar(int idReserva, Date fechaEntrada, Date fechaSalida, double valor, String formaPago) {
        return this.reservaDao.modificar(idReserva, fechaEntrada, fechaSalida, valor, formaPago);
    }

    public int eliminar(Integer idReserva) {
        return reservaDao.eliminar(idReserva);
    }

    public double calcularValor(JDateChooser fechaEntrada, JDateChooser fechaSalida) {

        if (fechaEntrada.getDate() != null && fechaSalida.getDate() != null) {

            int dias = -1;

            Calendar entrada = fechaEntrada.getCalendar();
            Calendar salida = fechaSalida.getCalendar();

            while (entrada.before(salida) || entrada.equals(salida)) {
                dias++;
                entrada.add(Calendar.DATE, 1);
            }
            return valorReserva * dias;
        } else {
            return 0.0;
        }

    }
}
