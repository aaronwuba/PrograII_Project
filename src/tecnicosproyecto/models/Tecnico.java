/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnicosproyecto.models;

/**
 *
 * @author KEYLO
 */
public class Tecnico {
    private int id;
    private String nombre;
    private String especialidad;
    private String contacto;
    private String estado;

    public Tecnico() {}

    public Tecnico(int id, String nombre, String especialidad, String contacto, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.contacto = contacto;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
    

