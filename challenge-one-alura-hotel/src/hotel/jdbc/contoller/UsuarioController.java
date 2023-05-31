package hotel.jdbc.contoller;

import hotel.jdbc.connection.ConeccionDb;
import hotel.jdbc.dao.UsuarioDao;

public class UsuarioController {
			
	   private final UsuarioDao usuarioDao;

	    public UsuarioController() {
	        var factory = new ConeccionDb();
	        this.usuarioDao = new UsuarioDao(factory.getConexion());
	    }

	    public boolean buscarUsuario(String usuario, String contrasena) {
	        return usuarioDao.buscarUsuario(usuario, contrasena);
	    }

}
