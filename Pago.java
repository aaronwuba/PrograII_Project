/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tallermecanico;

/**
 *
 * @author Wuba
 */
import java.time.LocalDate;

public class Pago {

    private int idPago;
    private LocalDate fecha;
    private double monto;
    private String metodoPago;
    //private OrdenServicio ordenServicio;
    //private String usuarioRegistro;

    public Pago(int idPago, LocalDate fecha, double monto,
            String metodoPago) {

        this.idPago = idPago;
        this.fecha = fecha;
        this.monto = monto;
        this.metodoPago = metodoPago;
    }


    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }


// Calcular el total de la orden



    @Override
    public String toString() {
        return "ID Pago: " + idPago
                + "\nFecha: " + fecha
                + "\nMonto: " + monto
                + "\nMétodo de pago: " + metodoPago;
    }

}
