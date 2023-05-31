package hotel.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hotel.jdbc.modelo.Huesped;

public class HuespedDao {
	private Connection con;

    public HuespedDao(Connection conexion) {
        this.con = conexion;
    }

    public List<Huesped> listar() {

        List<Huesped> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = con
                    .prepareStatement("SELECT * FROM huespedes");

            try (statement) {
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {

                    while (resultSet.next()) {
                        resultado.add(new Huesped(
                                resultSet.getInt("id"),
                                resultSet.getInt("idReserva"),
                                resultSet.getString("nombre"),
                                resultSet.getString("apellido"),
                                resultSet.getDate("fechaNacimiento"),
                                resultSet.getString("nacionalidad"),
                                resultSet.getString("telefono")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public List<Huesped> listarBusqueda(String cadena) {

        List<Huesped> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = con
                    .prepareStatement("SELECT * FROM huespedes WHERE apellido LIKE ?");

            try (statement) {
                statement.setString(1, cadena + "%");
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {

                    while (resultSet.next()) {
                        resultado.add(new Huesped(
                                resultSet.getInt("id"),
                                resultSet.getInt("idReserva"),
                                resultSet.getString("nombre"),
                                resultSet.getString("apellido"),
                                resultSet.getDate("fechaNacimiento"),
                                resultSet.getString("nacionalidad"),
                                resultSet.getString("telefono")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    
        return resultado;
    }

    public void guardar(Huesped huesped) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String nacimiento = date.format(huesped.getFechaNacimiento());

        try {
            PreparedStatement statement;

            statement = con.prepareStatement(
                    "INSERT INTO HUESPEDES "
                    + "(idReserva, nombre, apellido, fechaNacimiento, nacionalidad, telefono)"
                    + " VALUES (?, ?, ?, ?, ?, ?)"
            );

            try (statement) {

                statement.setInt(1, huesped.getId_reserva());
                statement.setString(2, huesped.getNombre());
                statement.setString(3, huesped.getApellido());
                statement.setDate(4, Date.valueOf(nacimiento));
                statement.setString(5, huesped.getNacionalidad());
                statement.setString(6, huesped.getTelefono());

                statement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int modificar(int idHuesped, int idReserva, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono) {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String nacimiento = date.format(fechaNacimiento);

        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE huespedes SET "
                    + " idReserva = ?, "
                    + " nombre = ?,"
                    + " apellido = ?,"
                    + " fechaNacimiento = ?,"
                    + " nacionalidad = ?,"
                    + " telefono = ?"
                    + " WHERE id = ?"
            );

            try (statement) {

                statement.setInt(1, idReserva);
                statement.setString(2, nombre);
                statement.setString(3, apellido);
                statement.setDate(4, Date.valueOf(nacimiento));
                statement.setString(5, nacionalidad);
                statement.setString(6, telefono);
                statement.setInt(7, idHuesped);

                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminar(int idHuesped) {

        try {

            PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id = ?");

            try (statement) {
                statement.setInt(1, idHuesped);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
