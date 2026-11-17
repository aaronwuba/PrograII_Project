package tallermecanico;
import java.sql.*;
/** Conexión opcional para los módulos SQL originales de citas y reportes. */
public class ConexionBD {
    public static Connection obtenerConexion() throws SQLException {
        String url=System.getenv().getOrDefault("TALLER_DB_URL", "jdbc:mysql://localhost:3306/tallermecanico");
        String usuario=System.getenv().getOrDefault("TALLER_DB_USER", "root");
        String clave=System.getenv().getOrDefault("TALLER_DB_PASSWORD", "");
        return DriverManager.getConnection(url,usuario,clave);
    }
}
