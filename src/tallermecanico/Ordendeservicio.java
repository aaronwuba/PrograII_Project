package tallermecanico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Ordenes de servicio del taller mecanico. */
public class Ordendeservicio {
    private final int numero;
    private final String identificacionCliente;
    private String placaVehiculo;
    private String descripcion;
    private String estado;
    private final LocalDate fecha;
    private double costoEstimado;

    private static final List<Ordendeservicio> ORDENES = new ArrayList<>();
    private static int siguienteNumero = 1;

    public Ordendeservicio(String identificacionCliente, String placaVehiculo,
                           String descripcion, double costoEstimado) {
        Clientes cliente = Clientes.buscar(identificacionCliente);
        if (cliente == null || !cliente.isActivo())
            throw new IllegalArgumentException("El cliente no existe o esta inactivo");
        if (placaVehiculo == null || placaVehiculo.trim().isEmpty())
            throw new IllegalArgumentException("La placa es obligatoria");
        if (descripcion == null || descripcion.trim().isEmpty())
            throw new IllegalArgumentException("La descripcion es obligatoria");
        if (costoEstimado < 0 || Double.isNaN(costoEstimado) || Double.isInfinite(costoEstimado))
            throw new IllegalArgumentException("El costo no es valido");
        this.numero = siguienteNumero++;
        this.identificacionCliente = identificacionCliente.trim();
        this.placaVehiculo = placaVehiculo.trim();
        this.descripcion = descripcion.trim();
        this.costoEstimado = costoEstimado;
        this.estado = "Pendiente";
        this.fecha = LocalDate.now();
    }

    public static Ordendeservicio registrar(String identificacionCliente, String placa,
                                             String descripcion, double costo) {
        Ordendeservicio orden = new Ordendeservicio(identificacionCliente, placa, descripcion, costo);
        ORDENES.add(orden);
        return orden;
    }

    public static Ordendeservicio buscar(int numero) {
        for (Ordendeservicio orden : ORDENES) {
            if (orden.numero == numero) return orden;
        }
        return null;
    }

    public static List<Ordendeservicio> listar() {
        return Collections.unmodifiableList(new ArrayList<>(ORDENES));
    }

    public static List<Ordendeservicio> listarPorCliente(String identificacion) {
        List<Ordendeservicio> resultado = new ArrayList<>();
        if (identificacion == null) return resultado;
        for (Ordendeservicio orden : ORDENES) {
            if (orden.identificacionCliente.equalsIgnoreCase(identificacion.trim())) resultado.add(orden);
        }
        return resultado;
    }

    public boolean cambiarEstado(String nuevoEstado) {
        if (nuevoEstado == null) return false;
        String valor = nuevoEstado.trim();
        if (!valor.equalsIgnoreCase("Pendiente") && !valor.equalsIgnoreCase("En proceso")
                && !valor.equalsIgnoreCase("Finalizada") && !valor.equalsIgnoreCase("Cancelada")) return false;
        if (estado.equalsIgnoreCase("Finalizada") || estado.equalsIgnoreCase("Cancelada")) return false;
        estado = valor;
        return true;
    }

    public boolean actualizar(String descripcion, double costoEstimado) {
        if (descripcion == null || descripcion.trim().isEmpty() || costoEstimado < 0
                || Double.isNaN(costoEstimado) || Double.isInfinite(costoEstimado)
                || estado.equalsIgnoreCase("Finalizada") || estado.equalsIgnoreCase("Cancelada")) return false;
        this.descripcion = descripcion.trim();
        this.costoEstimado = costoEstimado;
        return true;
    }

    public int getNumero() { return numero; }
    public String getIdentificacionCliente() { return identificacionCliente; }
    public String getPlacaVehiculo() { return placaVehiculo; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }
    public LocalDate getFecha() { return fecha; }
    public double getCostoEstimado() { return costoEstimado; }

    @Override
    public String toString() {
        return "Orden #" + numero + " | Cliente: " + identificacionCliente +
                " | Placa: " + placaVehiculo + " | Estado: " + estado;
    }
}
