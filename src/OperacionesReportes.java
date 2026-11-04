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
public class OperacionesReportes {
    
    public List<String[]> listarReporteCitas() {
        List<String[]> listaReporte = new ArrayList<>();
        
        String sql = "SELECT c.id_cita, cl.nombre AS cliente, v.placa, v.marca, c.fecha, c.hora, c.estado " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.id_cliente = cl.id_cliente " +
                     "JOIN vehiculos v ON c.placa_vehiculo = v.placa";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String[] fila = new String[7];
                fila[0] = String.valueOf(rs.getInt("id_cita"));
                fila[1] = rs.getString("cliente");
                fila[2] = rs.getString("placa");
                fila[3] = rs.getString("marca");
                fila[4] = rs.getString("fecha");
                fila[5] = rs.getString("hora");
                fila[6] = rs.getString("estado");
                
                listaReporte.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }

        return listaReporte;
    }
}
    

