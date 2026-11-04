package tallermecanico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Registro sencillo de clientes del taller. */
public class Clientes {
    private String identificacion;
    private String nombre;
    private String telefono;
    private String correo;
    private boolean activo;

    private static final List<Clientes> REGISTRO = new ArrayList<>();

    public Clientes(String identificacion, String nombre, String telefono, String correo) {
        if (identificacion == null || identificacion.trim().isEmpty())
            throw new IllegalArgumentException("La identificacion es obligatoria");
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre es obligatorio");
        this.identificacion = identificacion.trim();
        this.nombre = nombre.trim();
        this.telefono = telefono == null ? "" : telefono.trim();
        this.correo = correo == null ? "" : correo.trim();
        this.activo = true;
    }

    public static boolean registrar(Clientes cliente) {
        if (cliente == null || buscar(cliente.getIdentificacion()) != null) return false;
        REGISTRO.add(cliente);
        return true;
    }

    public static Clientes buscar(String identificacion) {
        if (identificacion == null) return null;
        for (Clientes cliente : REGISTRO) {
            if (cliente.identificacion.equalsIgnoreCase(identificacion.trim())) return cliente;
        }
        return null;
    }

    public static List<Clientes> listar() {
        return Collections.unmodifiableList(new ArrayList<>(REGISTRO));
    }

    public static boolean modificar(String identificacion, String nombre, String telefono, String correo) {
        Clientes cliente = buscar(identificacion);
        if (cliente == null || nombre == null || nombre.trim().isEmpty()) return false;
        cliente.nombre = nombre.trim();
        cliente.telefono = telefono == null ? "" : telefono.trim();
        cliente.correo = correo == null ? "" : correo.trim();
        return true;
    }

    public static boolean inactivar(String identificacion) {
        Clientes cliente = buscar(identificacion);
        if (cliente == null) return false;
        cliente.activo = false;
        return true;
    }

    public static boolean activar(String identificacion) {
        Clientes cliente = buscar(identificacion);
        if (cliente == null) return false;
        cliente.activo = true;
        return true;
    }

    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public boolean isActivo() { return activo; }

    @Override
    public String toString() {
        return identificacion + " - " + nombre + " - " + (activo ? "Activo" : "Inactivo");
    }
}
