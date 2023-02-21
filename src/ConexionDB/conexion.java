package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    Connection conn;

    private String host = "localhost";
    private String port = "3306";
    private String dbName = "sat";
    private String userName = "root";
    private String password = "danielizas";

    public conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName;
            conn = DriverManager.getConnection(url, this.userName, this.password);
            System.out.println("Conectado correctamente");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar " + e);
        }
    }

    public static void main(String[] args) {
        conexion c = new conexion();
    }

}
