package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static ThreadLocal<Connection> tlConnection = new ThreadLocal<>();

    public static Connection getConnection() {
        if (tlConnection.get() == null) {
            try {
                String url = ConfigReader.get("db.url");
                String user = ConfigReader.get("db.user");
                String password = ConfigReader.get("db.password");

                Connection conn = DriverManager.getConnection(url, user, password);
                tlConnection.set(conn);
            } catch (SQLException e) {
                throw new RuntimeException("❌ DB bağlantısı oluşturulamadı: " + e.getMessage(), e);
            }
        }
        return tlConnection.get();
    }

    public static void closeConnection() {
        Connection conn = tlConnection.get();
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("❌ DB bağlantısı kapatılamadı: " + e.getMessage());
            } finally {
                tlConnection.remove();  // ThreadLocal temizliği
            }
        }
    }
}
