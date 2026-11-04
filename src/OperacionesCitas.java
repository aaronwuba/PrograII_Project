/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julia
 */
public class OperacionesCitas {

    
    public boolean guardarCitas(Citas cita) {
        String sql = "INSERT INTO citas (id_cliente, placa_vehiculo, fecha, hora, motivo, estado, usuario_registra) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cita.getIdCliente());
            ps.setString(2, cita.getPlacaVehiculo());
            ps.setString(3, cita.getFecha());
            ps.setString(4, cita.getHora());
            ps.setString(5, cita.getMotivo());
            ps.setString(6, cita.getEstado());
            ps.setString(7, cita.getUsuarioRegistra());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al guardar la cita: " + e.getMessage());
            return false;
        }
    }

    
    public List<Citas> listarCitas() {
        List<Citas> listaCitas = new ArrayList<>();
        String sql = "SELECT * FROM citas";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Citas cita = new Citas();
                cita.setIdCita(rs.getInt("id_cita"));
                cita.setIdCliente(rs.getString("id_cliente"));
                cita.setPlacaVehiculo(rs.getString("placa_vehiculo"));
                cita.setFecha(rs.getString("fecha"));
                cita.setHora(rs.getString("hora"));
                cita.setMotivo(rs.getString("motivo"));
                cita.setEstado(rs.getString("estado"));
                cita.setUsuarioRegistra(rs.getString("usuario_registra"));
                
                listaCitas.add(cita);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar las citas: " + e.getMessage());
        }

        return listaCitas;
    }
}
    

