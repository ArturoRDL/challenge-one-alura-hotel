package hotel.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

import hotel.jdbc.modelo.Reserva;

public class ReservaDao {
	private Connection con;
	private int idReserva;
	
	public ReservaDao(Connection conexion) {
		this.con = conexion;
	}
	 public List<Reserva> listar() {

	        List<Reserva> resultado = new ArrayList<>();

	        try {
	            final PreparedStatement statement = con
	                    .prepareStatement("SELECT * FROM reservas");

	            try (statement) {
	                statement.execute();

	                final ResultSet resultSet = statement.getResultSet();

	                try (resultSet) {

	                    while (resultSet.next()) {
	                        resultado.add(new Reserva(
	                                resultSet.getInt("id"),
	                                resultSet.getDate("fechaEntrada"),
	                                resultSet.getDate("fechaSalida"),
	                                resultSet.getDouble("valor"),
	                                resultSet.getString("formaPago")
	                        ));
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        return resultado;
	    }
	    
	    public List<Reserva> listarBusqueda(int idBusqueda) {

	        List<Reserva> resultado = new ArrayList<>();

	        try {
	            final PreparedStatement statement = con
	                    .prepareStatement("SELECT * FROM reservas WHERE id = ?");

	            try (statement) {
	                statement.setInt(1, idBusqueda);
	                statement.execute();

	                final ResultSet resultSet = statement.getResultSet();

	                try (resultSet) {

	                    while (resultSet.next()) {
	                        resultado.add(new Reserva(
	                                resultSet.getInt("id"),
	                                resultSet.getDate("fechaEntrada"),
	                                resultSet.getDate("fechaSalida"),
	                                resultSet.getDouble("valor"),
	                                resultSet.getString("formaPago")
	                        ));
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        return resultado;
	    }

	    public int guardar(Reserva reserva) {

	        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	        String entrada = date.format(reserva.getFechaEntrada());
	        String salida = date.format(reserva.getFechaSalida());

	        try {

	            PreparedStatement statement;
	            statement = con.prepareStatement(
	                    "INSERT INTO RESERVAS "
	                    + "(fechaEntrada, fechaSalida, valor, formaPago)"
	                    + " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

	            try (statement) {
	                statement.setDate(1, Date.valueOf(entrada));
	                statement.setDate(2, Date.valueOf(salida));
	                statement.setDouble(3, reserva.getValor());
	                statement.setString(4, reserva.getFormaPago());

	                statement.execute();

	                ResultSet resultSet = statement.getGeneratedKeys();

	                try (resultSet) {

	                    while (resultSet.next()) {
	                        idReserva = resultSet.getInt(1);
	                    }

	                    return idReserva;
	                }
	            }

	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	    }

	    public int modificar(int idReserva, java.util.Date fechaEntrada, java.util.Date fechaSalida, double valor, String formaPago) {

	        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	        String entrada = date.format(fechaEntrada);
	        String salida = date.format(fechaSalida);

	        try {
	            final PreparedStatement statement = con.prepareStatement(
	                    "UPDATE reservas SET "
	                    + " fechaEntrada = ?, "
	                    + " fechaSalida = ?,"
	                    + " valor = ?,"
	                    + " formaPago = ?"
	                    + " WHERE id = ?"
	            );

	            try (statement) {

	                statement.setDate(1, Date.valueOf(entrada));
	                statement.setDate(2, Date.valueOf(salida));
	                statement.setDouble(3, valor);
	                statement.setString(4, formaPago);
	                statement.setInt(5, idReserva);

	                statement.execute();

	                int updateCount = statement.getUpdateCount();

	                return updateCount;
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    public int eliminar(Integer idReserva) {
	        try {

	            PreparedStatement statement = con.prepareStatement("DELETE FROM reservas WHERE id = ?");

	            try (statement) {
	                statement.setInt(1, idReserva);
	                statement.execute();

	                int updateCount = statement.getUpdateCount();

	                return updateCount;
	            }

	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
}
