package ConexionDB;

import java.sql.*;
import java.util.Scanner;

public class conexion {
    Connection conn;

    private Scanner s;
    private String host = "localhost";
    private String port = "3306";
    private String dbName = "sat";
    private String userName = "root";
    private String password = "danielizas";

    public conexion() {
        this.s = new Scanner(System.in);

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
        c.mostrarMenu();

    }

    public void mostrarMenu(){
        int opc =0;
        while (opc != 4){
            System.out.println("Ingrese una opcion:");
            System.out.println("1. Inserte nueva persona");
            System.out.println("2. Ver informacion");
            System.out.println("3. Ver toda la tabla");
            System.out.println("4. salir");
            opc = s.nextInt();

            switch (opc){
                case 1:
                    //solicitar datos
                    System.out.println("Ingrese el nombre:");
                    String nombre = s.next();
                    System.out.println("Ingrese el apellido:");
                    String apellido = s.next();
                    System.out.println("Ingrese el CUI o DPI:");
                    String cui = s.next();
                    System.out.println("Ingrese el municipio");
                    String muni = s.next();

                    try {
                        // se crea la sentencia SQL para insertar los datos
                        String sql = "INSERT INTO persona (Nombre, Apellido, Cui, municipio_idmunicipio) VALUES (?, ?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, nombre);
                        stmt.setString(2, apellido);
                        stmt.setString(3, cui);
                        stmt.setString(4, muni);

                        int filas = stmt.executeUpdate();
                        if (filas > 0) {
                            System.out.println("Registro insertado correctamente.");
                        } else {
                            System.out.println("No se pudo insertar el registro.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al insertar el registro: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Ingrese el DPI de la persona:");
                    String cuiBusqueda = s.next();
                    try {
                        // se crea la sentencia SQL para leer los datos de la persona
                        String sql = "SELECT * FROM persona WHERE cui = ?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, cuiBusqueda);

                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            System.out.println("Nombre: " + rs.getString("nombre"));
                            System.out.println("Apellido: " + rs.getString("apellido"));
                            System.out.println("CUI o DPI: " + rs.getString("cui"));
                            System.out.println("Municipio: " + rs.getString("municipio_idmunicipio"));
                        } else {
                            System.out.println("No se encontró la persona con el DPI ingresado.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al leer la información de la persona: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        //Sentencia para leer todos los datos de la tabla persona
                        String sql = "SELECT * FROM persona";
                        Statement stmt = conn.createStatement();

                        ResultSet rs = stmt.executeQuery(sql);

                        while (rs.next()) {
                            System.out.println("Nombre: " + rs.getString("nombre"));
                            System.out.println("Apellido: " + rs.getString("apellido"));
                            System.out.println("CUI o DPI: " + rs.getString("cui"));
                            System.out.println("Municipio: " + rs.getString("municipio_idmunicipio"));
                            System.out.println("--------------------------");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al leer la información de la tabla persona: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
            }
        }
    }

}
