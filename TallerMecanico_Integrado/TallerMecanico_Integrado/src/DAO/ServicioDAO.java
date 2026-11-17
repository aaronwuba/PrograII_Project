package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Servicio;

public class ServicioDAO {
    private final Connection conexion;

    public ServicioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean registrar(Servicio servicio) throws SQLException {
        String sql = "INSERT INTO servicios (nombre, descripcion, precio) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecio());
            return ps.executeUpdate() > 0;
        }
    }

    public ArrayList<Servicio> consultar() throws SQLException {
        ArrayList<Servicio> lista = new ArrayList<>();
        String sql = "SELECT idServicio, nombre, descripcion, precio FROM servicios ORDER BY idServicio";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Servicio servicio = new Servicio(
                        rs.getInt("idServicio"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio")
                );
                lista.add(servicio);
            }
        }
        return lista;
    }

    public boolean modificar(Servicio servicio) throws SQLException {
        String sql = "UPDATE servicios SET nombre = ?, descripcion = ?, precio = ? WHERE idServicio = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecio());
            ps.setInt(4, servicio.getIdServicio());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idServicio) throws SQLException {
        String sql = "DELETE FROM servicios WHERE idServicio = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idServicio);
            return ps.executeUpdate() > 0;
        }
    }
}
