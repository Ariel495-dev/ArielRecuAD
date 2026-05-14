public class Main {

    public static void main(String[] args) {
        // Ruta al fichero de base de datos (ajusta si es necesario)
        String dbPath = "recuperacionT1.sqlite3";

        DatabaseManager db = new DatabaseManager(dbPath);
        db.conectar();

        CsvExporter exporter = new CsvExporter(db);
        exporter.exportar("dump.csv");

        db.cerrar();
    }
}
