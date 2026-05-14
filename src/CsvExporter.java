import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CsvExporter {

    private final DatabaseManager db;

    public CsvExporter(DatabaseManager db) {
        this.db = db;
    }

    public void exportar(String rutaSalida) {
        // Obtenemos todos los usuarios
        List<Usuario> usuarios = obtenerUsuarios();

        // Para cada usuario, buscamos sus colores favoritos y construimos la línea CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaSalida))) {
            for (Usuario usuario : usuarios) {
                List<String> colores = obtenerColoresFavoritos(usuario.getId());

                // Construir línea: nombre,color1,color2,...
                StringBuilder linea = new StringBuilder(usuario.getNombre());
                for (String color : colores) {
                    linea.append(",").append(color);
                }

                writer.write(linea.toString());
                writer.newLine();

                System.out.println("Exportado: " + linea);
            }
            System.out.println("\nFichero CSV generado correctamente: " + rutaSalida);
        } catch (IOException e) {
            System.err.println("Error al escribir el fichero CSV: " + e.getMessage());
        }
    }

    private List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, name FROM Usuario";

        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("name");
                usuarios.add(new Usuario(id, nombre));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    private List<String> obtenerColoresFavoritos(int idUsuario) {
        List<String> colores = new ArrayList<>();

        // JOIN entre ColorFavorito y Color para obtener el nombre del color
        String sql = "SELECT c.name " +
                "FROM ColorFavorito cf " +
                "JOIN Color c ON cf.idColor = c.id " +
                "WHERE cf.idUsuario = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    colores.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar colores del usuario " + idUsuario + ": " + e.getMessage());
        }

        return colores;
    }
}

//ssaiifàlis`dga