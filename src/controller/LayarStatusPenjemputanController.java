package controller;

import view.LayarStatusPenjemputan;
import java.awt.Window;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.config;

public class LayarStatusPenjemputanController {
    private LayarStatusPenjemputan layarStatusPenjemputan;
    private int idPenjemputan;

    public LayarStatusPenjemputanController(LayarStatusPenjemputan layarStatusPenjemputan, int idPenjemputan) {
        this.layarStatusPenjemputan = layarStatusPenjemputan;
        this.idPenjemputan = idPenjemputan;
    }

    public void close() {
        Window window = SwingUtilities.getWindowAncestor(layarStatusPenjemputan);
        if (window != null) {
            window.dispose();
        }
    }

    public boolean cancelPenjemputan(int idPenjemputan) {
        try (Connection connection = config.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "DELETE FROM penjemputan WHERE idPenjemputan = ?")) {

            stmt.setInt(1, idPenjemputan);
            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getCurrentStatus(int idPenjemputan) {
        String status = null;
        String query = "SELECT statusPenjemputan FROM penjemputan WHERE idPenjemputan = ?";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idPenjemputan);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("statusPenjemputan");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
