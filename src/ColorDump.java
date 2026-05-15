import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ColorDump {

    public ColorDump() {
        volcarCSV();
    }

    public void volcarCSV() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:recuperacionT1.sqlite3");
            FileWriter writer = new FileWriter("dump.sql");

            Statement stmtUsuarios = conn.createStatement();
            ResultSet usuarios = stmtUsuarios.executeQuery("SELECT id, name FROM Usuario");

            while (usuarios.next()) {
                int idUsuario = usuarios.getInt("id");
                String nombreUsuario = usuarios.getString("name");

                StringBuilder linea = new StringBuilder(nombreUsuario);

                PreparedStatement stmtColores = conn.prepareStatement(
                        "SELECT c.name FROM ColorFavorito cf " +
                                "JOIN Color c ON cf.idColor = c.id " +
                                "WHERE cf.idUsuario = ?"
                );
                stmtColores.setInt(1, idUsuario);
                ResultSet colores = stmtColores.executeQuery();

                while (colores.next()) {
                    linea.append(",").append(colores.getString("name"));
                }

                writer.write(linea.toString());
                writer.write("\n");
                System.out.println("Exportado: " + linea);
            }

            writer.flush();
            writer.close();
            conn.close();
            System.out.println("dump.sql generado correctamente.");

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}