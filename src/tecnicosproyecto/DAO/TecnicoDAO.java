/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnicosproyecto.DAO;

import tecnicosproyecto.models.Tecnico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO {

    public boolean insertar(Tecnico t) {
        String sql = "INSERT INTO tecnico (nombre, especialidad, contacto, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getEspecialidad());
            ps.setString(3, t.getContacto());
            ps.setString(4, t.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar técnico: " + e.getMessage());
            return false;
        }
    }

    public List<Tecnico> listar() {
        List<Tecnico> lista = new ArrayList<>();
        String sql = "SELECT * FROM tecnico";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tecnico t = new Tecnico(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    rs.getString("contacto"),
                    rs.getString("estado")
                );
                lista.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar técnicos: " + e.getMessage());
        }
        return lista;
    }
}
