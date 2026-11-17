package tallermecanico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class App extends JFrame {
    private static final long serialVersionUID = 1L;
    private final File archivo = new File(System.getProperty("user.home"), "taller_alejandro_datos.ser");
    private Datos datos;
    private final DefaultTableModel tablaClientes = modelo("Identificación", "Nombre", "Teléfono", "Correo", "Estado");
    private final DefaultTableModel tablaOrdenes = modelo("N.º", "Cliente", "Vehículo", "Técnico", "Servicios", "Fecha", "Estado");
    private final JTextField buscar = new JTextField(18);
    private final JComboBox<String> filtro = new JComboBox<>(new String[]{"Todos", "Pendiente", "En proceso", "Finalizada", "Cancelada"});

    static class Cliente implements Serializable {
        private static final long serialVersionUID = 1L;
        String id, nombre, telefono, correo; boolean activo = true;
        Cliente(String id, String nombre, String telefono, String correo) { this.id=id; this.nombre=nombre; this.telefono=telefono; this.correo=correo; }
        public String toString() { return id + " - " + nombre; }
    }
    static class Orden implements Serializable {
        private static final long serialVersionUID = 1L;
        int numero; String clienteId, vehiculo, tecnico, servicios, observaciones, fecha, estado;
        Orden(int n, String c, String v, String t, String s, String o, String f, String e) { numero=n;clienteId=c;vehiculo=v;tecnico=t;servicios=s;observaciones=o;fecha=f;estado=e; }
    }
    static class Datos implements Serializable {
        private static final long serialVersionUID = 1L;
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Orden> ordenes = new ArrayList<>();
        int consecutivo = 1;
    }
    private static DefaultTableModel modelo(String... columnas) { return new DefaultTableModel(columnas,0) { public boolean isCellEditable(int r,int c){return false;} }; }
    private Datos cargar() {
        if (archivo.exists()) try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) { return (Datos)in.readObject(); }
        catch(Exception e) { JOptionPane.showMessageDialog(this,"No se pudieron cargar los datos: " + e.getMessage()); }
        return new Datos();
    }
    private void guardar() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) { out.writeObject(datos); }
        catch(IOException e) { JOptionPane.showMessageDialog(this,"Error al guardar: " + e.getMessage()); }
    }
    private Cliente cliente(String id) { for(Cliente c:datos.clientes) if(c.id.equals(id))return c; return null; }
    private void actualizar() {
        tablaClientes.setRowCount(0);
        for(Cliente c:datos.clientes) if((c.id+" "+c.nombre).toLowerCase().contains(buscar.getText().trim().toLowerCase()))
            tablaClientes.addRow(new Object[]{c.id,c.nombre,c.telefono,c.correo,c.activo?"Activo":"Inactivo"});
        tablaOrdenes.setRowCount(0);
        for(Orden o:datos.ordenes) if(filtro.getSelectedItem().equals("Todos") || filtro.getSelectedItem().equals(o.estado)) {
            Cliente c=cliente(o.clienteId);
            tablaOrdenes.addRow(new Object[]{o.numero,c==null?o.clienteId:c.nombre,o.vehiculo,o.tecnico,o.servicios,o.fecha,o.estado});
        }
    }
    private JPanel filaBotones(JButton... botones) { JPanel p=new JPanel(new FlowLayout(FlowLayout.LEFT));for(JButton b:botones)p.add(b);return p; }
    private JButton boton(String nombre,Runnable accion) { JButton b=new JButton(nombre);b.addActionListener(e->accion.run());return b; }
    private JPanel panelClientes() {
        JPanel p=new JPanel(new BorderLayout(8,8)); JTable tabla=new JTable(tablaClientes); tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel superior=new JPanel(new FlowLayout(FlowLayout.LEFT)); superior.add(new JLabel("Buscar por nombre o identificación:")); superior.add(buscar);
        buscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){ public void insertUpdate(javax.swing.event.DocumentEvent e){actualizar();} public void removeUpdate(javax.swing.event.DocumentEvent e){actualizar();} public void changedUpdate(javax.swing.event.DocumentEvent e){actualizar();} });
        p.add(superior,BorderLayout.NORTH);p.add(new JScrollPane(tabla),BorderLayout.CENTER);
        p.add(filaBotones(boton("Registrar",()->editarCliente(null)),boton("Modificar",()->{int r=tabla.getSelectedRow();if(r>=0)editarCliente(cliente(tablaClientes.getValueAt(r,0).toString()));else aviso("Seleccione un cliente.");}),boton("Activar / Inactivar",()->{int r=tabla.getSelectedRow();if(r<0){aviso("Seleccione un cliente.");return;}Cliente c=cliente(tablaClientes.getValueAt(r,0).toString());c.activo=!c.activo;guardar();actualizar();})),BorderLayout.SOUTH);
        return p;
    }
    private void aviso(String mensaje) { JOptionPane.showMessageDialog(this,mensaje); }
    private void editarCliente(Cliente original) {
        JTextField id=new JTextField(original==null?"":original.id), nombre=new JTextField(original==null?"":original.nombre),telefono=new JTextField(original==null?"":original.telefono),correo=new JTextField(original==null?"":original.correo);
        if(original!=null)id.setEditable(false);
        JPanel form=formulario(new String[]{"Identificación (9 a 12 dígitos)","Nombre completo","Teléfono","Correo"},new JComponent[]{id,nombre,telefono,correo});
        if(JOptionPane.showConfirmDialog(this,form,original==null?"Registrar cliente":"Modificar cliente",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION)return;
        String identificacion=id.getText().trim(), n=nombre.getText().trim(), t=telefono.getText().trim(), c=correo.getText().trim();
        if(!identificacion.matches("[0-9]{9,12}")){aviso("La identificación debe contener de 9 a 12 dígitos.");return;}
        if(n.isEmpty() || t.isEmpty()){aviso("Nombre y teléfono son obligatorios.");return;}
        if(!c.isEmpty() && !c.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")){aviso("El correo no tiene un formato válido.");return;}
        if(original==null){if(cliente(identificacion)!=null){aviso("Ya existe un cliente con esa identificación.");return;}datos.clientes.add(new Cliente(identificacion,n,t,c));}
        else {original.nombre=n;original.telefono=t;original.correo=c;}
        guardar();actualizar();
    }
    private JPanel formulario(String[] etiquetas,JComponent[] campos) {
        JPanel p=new JPanel(new GridLayout(etiquetas.length,2,8,8));p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        for(int i=0;i<etiquetas.length;i++){p.add(new JLabel(etiquetas[i]));p.add(campos[i]);}return p;
    }
    private Orden ordenSeleccionada(JTable tabla) {int r=tabla.getSelectedRow();if(r<0){aviso("Seleccione una orden.");return null;}int n=(Integer)tablaOrdenes.getValueAt(r,0);for(Orden o:datos.ordenes)if(o.numero==n)return o;return null;}
    private JPanel panelOrdenes() {
        JPanel p=new JPanel(new BorderLayout(8,8));JTable tabla=new JTable(tablaOrdenes);tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel arriba=new JPanel(new FlowLayout(FlowLayout.LEFT));arriba.add(new JLabel("Estado:"));arriba.add(filtro);filtro.addActionListener(e->actualizar());
        p.add(arriba,BorderLayout.NORTH);p.add(new JScrollPane(tabla),BorderLayout.CENTER);
        p.add(filaBotones(boton("Crear orden",()->editarOrden(null)),boton("Editar orden",()->{Orden o=ordenSeleccionada(tabla);if(o!=null)editarOrden(o);}),boton("Ver detalle",()->{Orden o=ordenSeleccionada(tabla);if(o!=null)aviso("Orden #"+o.numero+"\nCliente: "+o.clienteId+"\nVehículo: "+o.vehiculo+"\nTécnico: "+o.tecnico+"\nServicios: "+o.servicios+"\nFecha: "+o.fecha+"\nEstado: "+o.estado+"\nObservaciones: "+o.observaciones);}),boton("Cambiar estado",()->{Orden o=ordenSeleccionada(tabla);if(o==null)return;String[] estados={"Pendiente","En proceso","Finalizada","Cancelada"};String e=(String)JOptionPane.showInputDialog(this,"Nuevo estado:","Estado de orden",JOptionPane.QUESTION_MESSAGE,null,estados,o.estado);if(e!=null){o.estado=e;guardar();actualizar();}})),BorderLayout.SOUTH);
        return p;
    }
    private void editarOrden(Orden original) {
        ArrayList<Cliente> activos=new ArrayList<>();for(Cliente c:datos.clientes)if(c.activo || (original!=null && c.id.equals(original.clienteId)))activos.add(c);
        if(activos.isEmpty()){aviso("Primero registre un cliente activo.");return;}
        JComboBox<Cliente> clientes=new JComboBox<>(activos.toArray(new Cliente[0]));
        if(original!=null)for(Cliente c:activos)if(c.id.equals(original.clienteId))clientes.setSelectedItem(c);
        JTextField vehiculo=new JTextField(original==null?"":original.vehiculo),tecnico=new JTextField(original==null?"":original.tecnico),servicios=new JTextField(original==null?"":original.servicios),fecha=new JTextField(original==null?LocalDate.now().toString():original.fecha);
        JTextArea obs=new JTextArea(original==null?"":original.observaciones,3,20);JComboBox<String> estado=new JComboBox<>(new String[]{"Pendiente","En proceso","Finalizada","Cancelada"});if(original!=null)estado.setSelectedItem(original.estado);
        JPanel form=formulario(new String[]{"Cliente","Vehículo (placa)","Técnico responsable","Servicios (separar con coma)","Fecha (AAAA-MM-DD)","Estado","Observaciones"},new JComponent[]{clientes,vehiculo,tecnico,servicios,fecha,estado,new JScrollPane(obs)});
        if(JOptionPane.showConfirmDialog(this,form,original==null?"Crear orden":"Editar orden",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE)!=JOptionPane.OK_OPTION)return;
        String v=vehiculo.getText().trim(),t=tecnico.getText().trim(),s=servicios.getText().trim(),f=fecha.getText().trim();
        if(v.isEmpty()||t.isEmpty()||s.isEmpty()){aviso("Vehículo, técnico y servicios son obligatorios.");return;}
        try {LocalDate.parse(f);}catch(Exception ex){aviso("La fecha debe tener formato AAAA-MM-DD y ser válida.");return;}
        String c=((Cliente)clientes.getSelectedItem()).id,e=(String)estado.getSelectedItem();
        if(original==null)datos.ordenes.add(new Orden(datos.consecutivo++,c,v,t,s,obs.getText().trim(),f,e));
        else {original.clienteId=c;original.vehiculo=v;original.tecnico=t;original.servicios=s;original.observaciones=obs.getText().trim();original.fecha=f;original.estado=e;}
        guardar();actualizar();
    }
    public App() {
        super("Taller Mecánico - Módulos de Alejandro");datos=cargar();setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);setSize(1000,550);setLocationRelativeTo(null);
        JTabbedPane tabs=new JTabbedPane();tabs.addTab("Clientes",panelClientes());tabs.addTab("Órdenes de servicio",panelOrdenes());add(tabs);actualizar();
    }
    public static void main(String[] args) {SwingUtilities.invokeLater(()->new App().setVisible(true));}
}
