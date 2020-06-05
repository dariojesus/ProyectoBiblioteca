package prBiblioteca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class Vista extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conexion = null;
	private JTabbedPane pestañas;
	
	private JTextField codAutor;
	private JTextField nombreAutor;
	private JTextField nacionalidad;
	private JTextField codLibro;
	private JTextField titulo;
	private JTextField año;
	private JTextArea url;
	private JLabel imagen;
	private JList<Libro> disponibles;
	private DefaultListModel<Libro> modelDisponibles = new DefaultListModel<Libro>();;
	private JButton bGuardaLibro;
	private JButton bBorraLibro;
	private JButton bLimpiar;
	
	private JTextField codSocio;
	private JTextField nombreSocio;
	private JTextField apellidos;
	private JTextField telefono;
	private JTextField direccion;
	
	private JList <Socio> socios;
	private DefaultListModel<Socio> modelSocios = new DefaultListModel<Socio>();
	private JList <Libro> libros;
	private DefaultListModel<Prestamo> modelPrestamos = new DefaultListModel<Prestamo>();
	private JList<Prestamo> prestamos;
	private JButton prestar;
	private JButton devolver;
	
		public Vista() throws SQLException, MalformedURLException {
		
		// Cambiar el Look and Feel
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		
		//Dialogo de inicio con la conexion
		DialogoLogin d = new DialogoLogin();
		conexion = d.getConexion();
		
		
		// Crear un JTabbedPane (pestañas)
		pestañas = new JTabbedPane(JTabbedPane.LEFT);

		pestañas.addTab("Gestión de libros", panelLibro());
		pestañas.setToolTipTextAt(0, "Permite agregar, eliminar y consultar los libros de la base de datos.");
		pestañas.setIconAt(0, new ImageIcon("imagenes/IconoPeli.jpg"));
		
		pestañas.addTab("Gestión de socios", panelSocio());
		pestañas.setToolTipTextAt(1, "Permite agregar socios a la base de datos.");
		pestañas.setIconAt(1, new ImageIcon("imagenes/IconoPeli.jpg"));
		
		pestañas.addTab("Gestión de prestamos", panelPrestamo());
		pestañas.setToolTipTextAt(2, "Permite prestar, devolver y consultar prestamos de los socios.");
		pestañas.setIconAt(2, new ImageIcon("imagenes/IconoPeli.jpg"));
		

		this.setLayout(new GridLayout(1,1));
		this.add(pestañas);
	}
	
	private JPanel panelLibro() throws SQLException, MalformedURLException {
		
		JPanel p = new JPanel ();
		p.setLayout(new GridLayout(1,2));
		
		//Se prepara el panel con los datos del libro que se quiere borrar/agregar
			JPanel interno = new JPanel();
			interno.setLayout(new BorderLayout());
			interno.setBorder(new TitledBorder("Datos del libro"));
			
				JPanel exAgrega = new JPanel();
			
				JPanel agrega = new JPanel();
				agrega.setLayout(new GridLayout(7,2,5,10));
				
				agrega.add(new JLabel("Codigo autor: "));
				codAutor = new JTextField();
				codAutor.setEditable(false);
				codAutor.setBackground(Color.gray);
				agrega.add(codAutor);
				
				agrega.add(new JLabel("Nombre autor: "));
				nombreAutor = new JTextField();
				agrega.add(nombreAutor);
				
				agrega.add(new JLabel("Nacionalidad: "));
				nacionalidad = new JTextField();
				agrega.add(nacionalidad);
				
				agrega.add(new JLabel("Codigo del Libro: "));
				codLibro = new JTextField();
				codLibro.setEditable(false);
				codLibro.setBackground(Color.gray);
				agrega.add(codLibro);
				
				agrega.add(new JLabel("Titulo del libro: "));
				titulo = new JTextField();
				agrega.add(titulo);
				
				agrega.add(new JLabel("Año de publicación: "));
				año = new JTextField();
				agrega.add(año);
				
				agrega.add(new JLabel("URL imagen: "));
				url = new JTextArea();
				agrega.add(url);
				
				exAgrega.add(agrega);
				interno.add(exAgrega,BorderLayout.CENTER);
				
				JPanel botones = new JPanel ();
				bGuardaLibro = new JButton("Guardar");
				bBorraLibro = new JButton("Borrar");
				bBorraLibro.setEnabled(false);
				bLimpiar = new JButton("Limpiar");
				botones.add(bGuardaLibro);
				botones.add(bLimpiar);
				botones.add(bBorraLibro);
				
				interno.add(botones,BorderLayout.SOUTH);
				
		//Se prepara el panel con la lista de libros disponibles en la base de datos
			JPanel interno2 = new JPanel();
			interno2.setBorder(new TitledBorder("Libros disponibles"));
			
				modelDisponibles.addAll(this.listarLibros());
				disponibles = new JList<Libro>();
				disponibles.setModel(modelDisponibles);
				
					
					JSplitPane split = new JSplitPane();
					split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			
					JScrollPane scroll = new JScrollPane(disponibles);
					scroll.setPreferredSize(new Dimension(400,95));
					split.setLeftComponent(scroll);
					
					JScrollPane scrollImagen = new JScrollPane();
					scrollImagen.setPreferredSize(new Dimension(400,400));
					scrollImagen.setBackground(Color.WHITE);
					split.setRightComponent(scrollImagen);
					
					imagen = new JLabel();
					imagen.setIcon(new ImageIcon());
					imagen.setHorizontalAlignment(SwingConstants.CENTER);
					scrollImagen.setViewportView(imagen);
					validate();
			
				interno2.add(split);
			
		p.add(interno,BorderLayout.EAST);
		p.add(interno2,BorderLayout.WEST);
		
	
		return p;
		
	}

	private JPanel panelSocio() throws SQLException {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1,1));
		
			JPanel flow = new JPanel();
				JPanel interno = new JPanel();
				interno.setLayout(new GridLayout(5,2,5,10));
				interno.setBorder(new TitledBorder("Datos del socio"));
				
				interno.add(new JLabel("Código socio: "));
				codSocio = new JTextField("");
				codSocio.setEditable(false);
				interno.add(codSocio);
				
				interno.add(new JLabel("Nombre: "));
				nombreSocio = new JTextField("");
				interno.add(nombreSocio);
				
				interno.add(new JLabel("Apellidos: "));
				apellidos = new JTextField("");
				interno.add(apellidos);
				
				interno.add(new JLabel("Teléfono: "));
				telefono = new JTextField("");
				interno.add(telefono);
				
				interno.add(new JLabel("Dirección: "));
				direccion = new JTextField("");
				interno.add(direccion);
			flow.add(interno);
		
			p.add(flow);
		
		return p;
	}
	
	private JPanel panelPrestamo() throws SQLException {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,1));
		
			JPanel superior = new JPanel();
			superior.setLayout(new BorderLayout());
			superior.setBorder(new TitledBorder("Prestar libros"));
			
				//Se prepara el panel con la lista de libros disponibles en la base de datos
				JPanel interno1 = new JPanel();
				interno1.setBorder(new TitledBorder("Libros disponibles"));
				
				libros = new JList<Libro>();
				libros.setModel(modelDisponibles);
				
				JScrollPane scroll = new JScrollPane(libros);
				scroll.setPreferredSize(new Dimension(400,150));
				
				interno1.add(scroll);
				
				//Se prepara el panel con la lista de socios
				JPanel interno2 = new JPanel();
				interno2.setBorder(new TitledBorder("Socios de la biblioteca"));
				
				modelSocios.addAll(this.listarSocios());
				socios = new JList<Socio>();
				socios.setModel(modelSocios);
				
				JScrollPane scroll2 = new JScrollPane(socios);
				scroll2.setPreferredSize(new Dimension(400,150));
				
				interno2.add(scroll2);
				
				//Se prepara el panel para el boton
				JPanel pboton = new JPanel();
				prestar = new JButton("Realizar prestamo");
				pboton.add(prestar);
				
			superior.add(interno1,BorderLayout.WEST);
			superior.add(interno2,BorderLayout.EAST);
			superior.add(pboton,BorderLayout.SOUTH);
			
			
			JPanel inferior = new JPanel();
			inferior.setBorder(new TitledBorder("Prestamos activos"));
			
				
				JPanel interno3 = new JPanel();
				interno3.setLayout(new BorderLayout());
				
				//Se prepara el panel con la lista de prestamos
				modelPrestamos.addAll(this.listarPrestamos());
				prestamos = new JList<Prestamo>();
				prestamos.setModel(modelPrestamos);
				
				JScrollPane scroll3 = new JScrollPane(prestamos);
				scroll3.setPreferredSize(new Dimension(400,150));
				
				
				//Se prepara el panel con el boton
				JPanel pboton2 = new JPanel();
				devolver = new JButton("Devolver libro");
				pboton2.add(devolver);
				
				interno3.add(scroll3,BorderLayout.CENTER);
				interno3.add(pboton2,BorderLayout.SOUTH);
				
			inferior.add(interno3);
			
			
		p.add(superior);
		p.add(inferior);
		
		return p;
	}
	
	public ArrayList<Libro> listarLibros() throws SQLException{
		
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM AUTOR A JOIN LIBRO L ON (A.CODAUTOR=L.CODAUTOR) ORDER BY L.TITULO");
		
		
		ArrayList<Libro> lista = new ArrayList<Libro>();

		while(rs.next()) 
			lista.add(new Libro(rs.getInt(1),rs.getInt(4),rs.getString(2),rs.getString(3),rs.getString(5),rs.getString(6),rs.getString(8)));
		
		st.close();
		
		return lista;
	}
	
	public ArrayList<Socio> listarSocios() throws SQLException{
		
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM SOCIO ORDER BY NOMBRE");
		
		
		ArrayList<Socio> lista = new ArrayList<Socio>();

		while(rs.next()) 
			lista.add(new Socio(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
		
		st.close();
		
		return lista;
	}

	public ArrayList <Prestamo> listarPrestamos() throws SQLException {
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT P.codPrestamo,L.titulo,S.nombre,S.apellidos,P.fecha_prestamo,P.fecha_devolucion FROM LIBRO L JOIN PRESTAMOS P USING (codLibro) JOIN SOCIO S USING (codSocio)");
		
		ArrayList<Prestamo> lista = new ArrayList<Prestamo>();

		while(rs.next()) 
			lista.add(new Prestamo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
		
		st.close();
		
		return lista;
	}

	public void control (Controlador ctr) {
		disponibles.addListSelectionListener(ctr);
		bGuardaLibro.addActionListener(ctr);
		bLimpiar.addActionListener(ctr);
		bBorraLibro.addActionListener(ctr);
		devolver.addActionListener(ctr);
		prestar.addActionListener(ctr);
	}
	
	
	public JList<Libro> getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(JList<Libro> disponibles) {
		this.disponibles = disponibles;
	}

	public JButton getbGuardaLibro() {
		return bGuardaLibro;
	}

	public void setbGuardaLibro(JButton bGuardaLibro) {
		this.bGuardaLibro = bGuardaLibro;
	}

	public JButton getbBorraLibro() {
		return bBorraLibro;
	}

	public void setbBorraLibro(JButton bBorraLibro) {
		this.bBorraLibro = bBorraLibro;
	}

	public JTextField getCodSocio() {
		return codSocio;
	}

	public void setCodSocio(JTextField codSocio) {
		this.codSocio = codSocio;
	}

	public JTextField getNombreSocio() {
		return nombreSocio;
	}

	public void setNombreSocio(JTextField nombreSocio) {
		this.nombreSocio = nombreSocio;
	}

	public JTextField getApellidos() {
		return apellidos;
	}

	public void setApellidos(JTextField apellidos) {
		this.apellidos = apellidos;
	}

	public JTextField getTelefono() {
		return telefono;
	}

	public void setTelefono(JTextField telefono) {
		this.telefono = telefono;
	}

	public JTextField getDireccion() {
		return direccion;
	}

	public void setDireccion(JTextField direccion) {
		this.direccion = direccion;
	}

	public JList<Socio> getSocios() {
		return socios;
	}

	public void setSocios(JList<Socio> socios) {
		this.socios = socios;
	}

	public JList<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(JList<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

	public JButton getPrestar() {
		return prestar;
	}

	public void setPrestar(JButton prestar) {
		this.prestar = prestar;
	}

	public JButton getDevolver() {
		return devolver;
	}

	public void setDevolver(JButton devolver) {
		this.devolver = devolver;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public JTabbedPane getPestañas() {
		return pestañas;
	}

	public void setPestañas(JTabbedPane pestañas) {
		this.pestañas = pestañas;
	}

	public JTextField getNombreAutor() {
		return nombreAutor;
	}

	public void setNombreAutor(JTextField nombreAutor) {
		this.nombreAutor = nombreAutor;
	}

	public JTextField getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(JTextField nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public JTextField getTitulo() {
		return titulo;
	}

	public void setTitulo(JTextField titulo) {
		this.titulo = titulo;
	}

	public JTextField getAño() {
		return año;
	}

	public void setAño(JTextField año) {
		this.año = año;
	}

	public JList<Libro> getLibros() {
		return libros;
	}

	public void setLibros(JList<Libro> libros) {
		this.libros = libros;
	}

	public JTextField getCodAutor() {
		return codAutor;
	}

	public void setCodAutor(JTextField codAutor) {
		this.codAutor = codAutor;
	}

	public JTextField getCodLibro() {
		return codLibro;
	}

	public void setCodLibro(JTextField codLibro) {
		this.codLibro = codLibro;
	}


	public DefaultListModel<Libro> getModelDisponibles() {
		return modelDisponibles;
	}

	public void setModelDisponibles(DefaultListModel<Libro> modelDisponibles) {
		this.modelDisponibles = modelDisponibles;
	}

	public DefaultListModel<Socio> getModelSocios() {
		return modelSocios;
	}

	public void setModelSocios(DefaultListModel<Socio> modelSocios) {
		this.modelSocios = modelSocios;
	}

	public DefaultListModel<Prestamo> getModelPrestamos() {
		return modelPrestamos;
	}

	public void setModelPrestamos(DefaultListModel<Prestamo> modelPrestamos) {
		this.modelPrestamos = modelPrestamos;
	}

	public JTextArea getUrl() {
		return url;
	}

	public void setUrl(JTextArea url) {
		this.url = url;
	}

	public JButton getbLimpiar() {
		return bLimpiar;
	}

	public void setbLimpiar(JButton bLimpiar) {
		this.bLimpiar = bLimpiar;
	}

	public JLabel getImagen() {
		return imagen;
	}

	public void setImagen(JLabel imagen) {
		this.imagen = imagen;
	}

	

}
