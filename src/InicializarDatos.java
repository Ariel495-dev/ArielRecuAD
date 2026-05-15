import java.sql.*;

public class InicializarDatos {

    public InicializarDatos() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:recuperacionT1.sqlite3");
            Statement stmt = conn.createStatement();


            stmt.executeUpdate("DELETE FROM ColorFavorito WHERE idUsuario = 3");


            stmt.executeUpdate("INSERT INTO ColorFavorito (idUsuario, idColor) VALUES (3, 2)");  // azul
            stmt.executeUpdate("INSERT INTO ColorFavorito (idUsuario, idColor) VALUES (3, 6)");  // negro
            stmt.executeUpdate("INSERT INTO ColorFavorito (idUsuario, idColor) VALUES (3, 7)");  // amarillo

            conn.close();
            System.out.println("Datos inicializados correctamente.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}