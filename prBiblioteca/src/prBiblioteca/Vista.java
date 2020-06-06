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
import javax.swing.table.DefaultTableModel;


public class Vista extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conexion = null;
	private JTabbedPane pestañas;
	
	//Campos accesibles del panel gestion de libros
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
	
	//Campos accesibles del panel gestion de socios
	private JTextField codSocio;
	private JTextField nombreSocio;
	private JTextField apellidos;
	private JTextField telefono;
	private JTextField direccion;
	private JTable tablaSocios;
	private DefaultTableModel modelTablaSocios;
	private JButton bGuardaSocio;
	private JButton bBorraSocio;
	private JButton bLimpiaSocio;
	
	//Campos accesibles del panel de gestion de prestamos
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
		
		pestañas.addTab("Gestión de socios", panelSocio());
		pestañas.setToolTipTextAt(1, "Permite agregar socios a la base de datos.");
		
		pestañas.addTab("Gestión de prestamos", panelPrestamo());
		pestañas.setToolTipTextAt(2, "Permite prestar, devolver y consultar prestamos de los socios.");
		
		this.setLayout(new GridLayout(1,1));
		this.add(pestañas);
	}
	
	private JPanel panelLibro() throws SQLException, MalformedURLException {
		
		JPanel p = new JPanel ();
		p.setLayout(null);
		
		JPanel Campos = new JPanel();
		Campos.setLayout(null);
		Campos.setBounds(10, 25, 310, 317);
		p.add(Campos);
		
		
			//Añadimos los campos de texto
			codAutor = new JTextField();
			codAutor.setBackground(Color.GRAY);
			codAutor.setEditable(false);
			codAutor.setColumns(10);
			codAutor.setBounds(168, 32, 96, 26);
			Campos.add(codAutor);
			
			nombreAutor = new JTextField();
			nombreAutor.setColumns(10);
			nombreAutor.setBounds(168, 61, 96, 26);
			Campos.add(nombreAutor);
			
			nacionalidad = new JTextField();
			nacionalidad.setColumns(10);
			nacionalidad.setBounds(168, 90, 96, 26);
			Campos.add(nacionalidad);
			
			codLibro = new JTextField();
			codLibro.setEditable(false);
			codLibro.setBackground(Color.gray);
			codLibro.setColumns(10);
			codLibro.setBounds(168, 119, 96, 26);
			Campos.add(codLibro);
			
			titulo = new JTextField();
			titulo.setColumns(10);
			titulo.setBounds(168, 148, 96, 26);
			Campos.add(titulo);
			
			año = new JTextField();
			año.setColumns(10);
			año.setBounds(168, 177, 96, 26);
			Campos.add(año);
			
			url = new JTextArea();
			url.setBounds(168, 220, 114, 42);
			Campos.add(url);
		
			
			//Añadimos las etiquetas
			JLabel lautor = new JLabel("Código autor: ");
			lautor.setBounds(43, 32, 96, 19);
			Campos.add(lautor);
			
			JLabel lnombre = new JLabel("Nombre autor: ");
			lnombre.setBounds(43, 61, 96, 19);
			Campos.add(lnombre);
			
			JLabel lnacion = new JLabel("Nacionalidad: ");
			lnacion.setBounds(43, 93, 96, 19);
			Campos.add(lnacion);
			
			JLabel llibro = new JLabel("Código libro: ");
			llibro.setBounds(43, 122, 96, 19);
			Campos.add(llibro);
			
			JLabel ltitulo = new JLabel("Titulo: ");
			ltitulo.setBounds(43, 151, 96, 19);
			Campos.add(ltitulo);
			
			JLabel laño = new JLabel("Año publicación: ");
			laño.setBounds(43, 180, 96, 19);
			Campos.add(laño);
			
			JLabel limagen = new JLabel("Url imagen: ");
			limagen.setBounds(43, 226, 96, 19);
			Campos.add(limagen);
			
			
			//Añadimos los botones
			bGuardaLibro = new JButton("Guardar");
			bGuardaLibro.setBounds(330, 52, 85, 21);
			p.add(bGuardaLibro);
			
			bBorraLibro = new JButton("Borrar");
			bBorraLibro.setBounds(330, 83, 85, 21);
			bBorraLibro.setEnabled(false);
			p.add(bBorraLibro);
			
			bLimpiar = new JButton("Limpiar");
			bLimpiar.setBounds(330, 114, 85, 21);
			p.add(bLimpiar);
			
			JSeparator separator = new JSeparator();
			separator.setBounds(10, 366, 415, 2);
			p.add(separator);
			
	//Creamos el scroll pane para las imagenes de las portadas		
	JScrollPane scrollImagen = new JScrollPane();
	scrollImagen.setBounds(435, 10, 387, 525);
	p.add(scrollImagen);
	
	imagen = new JLabel();
	imagen.setIcon(new ImageIcon());
	imagen.setHorizontalAlignment(SwingConstants.CENTER);
	scrollImagen.setViewportView(imagen);
	
	//Creamos un modelo de libros para mostrar en los libros disponibles y su scrollPane
	modelDisponibles.addAll(this.listarLibros());
	disponibles = new JList<Libro>();
	disponibles.setModel(modelDisponibles);
	
	JScrollPane scrollDisponibles = new JScrollPane(disponibles);
	scrollDisponibles.setBounds(9, 392, 389, 125);
	p.add(scrollDisponibles);
						
	return p;
		
	}

	private JPanel panelSocio() throws SQLException {
		JPanel p = new JPanel();
		p.setLayout(null);
		
			//Creamos el panel con los campos de los datos de los usuarios
			JPanel Campos = new JPanel();
			Campos.setBorder(new TitledBorder("Datos del socio"));
			Campos.setBounds(10, 10, 311, 221);
			p.add(Campos);
			Campos.setLayout(null);
			
				//Creamos las cajas de texto
				codSocio = new JTextField();
				codSocio.setBounds(168, 32, 96, 26);
				codSocio.setEditable(false);
				codSocio.setBackground(Color.GRAY);
				Campos.add(codSocio);
				codSocio.setColumns(10);
				
				nombreSocio = new JTextField();
				nombreSocio.setBounds(168, 61, 96, 26);
				Campos.add(nombreSocio);
				nombreSocio.setColumns(10);
				
				apellidos = new JTextField();
				apellidos.setBounds(168, 90, 96, 26);
				Campos.add(apellidos);
				apellidos.setColumns(10);
				
				telefono = new JTextField();
				telefono.setBounds(168, 119, 96, 26);
				Campos.add(telefono);
				telefono.setColumns(10);
				
				direccion = new JTextField();
				direccion.setBounds(168, 148, 96, 26);
				Campos.add(direccion);
				direccion.setColumns(10);
				
				//Creamos las etiquetas
				JLabel lcodSocio = new JLabel("Codigo socio: ");
				lcodSocio.setBounds(78, 35, 78, 13);
				Campos.add(lcodSocio);
				
				JLabel lnombreSocio = new JLabel("Nombre: ");
				lnombreSocio.setBounds(78, 64, 78, 13);
				Campos.add(lnombreSocio);
				
				JLabel lApellidos = new JLabel("Apellidos: ");
				lApellidos.setBounds(78, 93, 78, 13);
				Campos.add(lApellidos);
				
				JLabel lTelefono = new JLabel("Teléfono: ");
				lTelefono.setBounds(78, 122, 78, 13);
				Campos.add(lTelefono);
				
				JLabel lDirección = new JLabel("Dirección: ");
				lDirección.setBounds(78, 151, 78, 13);
				Campos.add(lDirección);
				
				//Añadimos los botones
				bGuardaSocio = new JButton("Guardar");
				bGuardaSocio.setBounds(21, 190, 85, 21);
				Campos.add(bGuardaSocio);
				
				bBorraSocio = new JButton("Borrar");
				bBorraSocio.setEnabled(false);
				bBorraSocio.setBounds(112, 190, 85, 21);
				Campos.add(bBorraSocio);
				
				bLimpiaSocio = new JButton("Limpiar");
				bLimpiaSocio.setBounds(207, 190, 85, 21);
				Campos.add(bLimpiaSocio);
				
			
		//Se crea el scrollpane y la tabla para mostrar la informacion de la base de datos
		JScrollPane scrollSocios = new JScrollPane();
		scrollSocios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollSocios.setBounds(331, 10, 504, 553);
		p.add(scrollSocios);
		 	
			//En esta parte preparamos el modelo de la tabla
			modelTablaSocios = new DefaultTableModel();
			modelTablaSocios.addColumn("Código Socio");
			modelTablaSocios.addColumn("Nombre");
			modelTablaSocios.addColumn("Apellidos");
			modelTablaSocios.addColumn("Teléfono");
			modelTablaSocios.addColumn("Dirección");
			
			for (Socio s : this.listarSocios()) {
				modelTablaSocios.addRow(new Object[] {s.getCodSocio(),s.getNombre(),s.getApellidos(),s.getTelefono(),s.getDireccion()});
			}
			
		
		
		tablaSocios = new JTable();
		scrollSocios.setViewportView(tablaSocios);
		tablaSocios.setModel(modelTablaSocios);
		tablaSocios.getColumnModel().getColumn(0).setPreferredWidth(74);
		tablaSocios.getColumnModel().getColumn(1).setPreferredWidth(63);
		tablaSocios.getColumnModel().getColumn(2).setPreferredWidth(80);
		tablaSocios.getColumnModel().getColumn(3).setPreferredWidth(85);
		tablaSocios.getColumnModel().getColumn(4).setPreferredWidth(83);
		
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
		//Listeners del panel lirbo
		disponibles.addListSelectionListener(ctr);
		bGuardaLibro.addActionListener(ctr);
		bLimpiar.addActionListener(ctr);
		bBorraLibro.addActionListener(ctr);
		
		//Listeners del panel socios
		tablaSocios.getSelectionModel().addListSelectionListener(ctr);
		bGuardaSocio.addActionListener(ctr);
		bBorraSocio.addActionListener(ctr);
		bLimpiaSocio.addActionListener(ctr);
		
		//Listeners del panel prestamos
		devolver.addActionListener(ctr);
		prestar.addActionListener(ctr);
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

	public JTextField getCodAutor() {
		return codAutor;
	}

	public void setCodAutor(JTextField codAutor) {
		this.codAutor = codAutor;
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

	public JTextField getCodLibro() {
		return codLibro;
	}

	public void setCodLibro(JTextField codLibro) {
		this.codLibro = codLibro;
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

	public JTextArea getUrl() {
		return url;
	}

	public void setUrl(JTextArea url) {
		this.url = url;
	}

	public JLabel getImagen() {
		return imagen;
	}

	public void setImagen(JLabel imagen) {
		this.imagen = imagen;
	}

	public JList<Libro> getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(JList<Libro> disponibles) {
		this.disponibles = disponibles;
	}

	public DefaultListModel<Libro> getModelDisponibles() {
		return modelDisponibles;
	}

	public void setModelDisponibles(DefaultListModel<Libro> modelDisponibles) {
		this.modelDisponibles = modelDisponibles;
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

	public JButton getbLimpiar() {
		return bLimpiar;
	}

	public void setbLimpiar(JButton bLimpiar) {
		this.bLimpiar = bLimpiar;
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

	public DefaultListModel<Socio> getModelSocios() {
		return modelSocios;
	}

	public void setModelSocios(DefaultListModel<Socio> modelSocios) {
		this.modelSocios = modelSocios;
	}

	public JList<Libro> getLibros() {
		return libros;
	}

	public void setLibros(JList<Libro> libros) {
		this.libros = libros;
	}

	public DefaultListModel<Prestamo> getModelPrestamos() {
		return modelPrestamos;
	}

	public void setModelPrestamos(DefaultListModel<Prestamo> modelPrestamos) {
		this.modelPrestamos = modelPrestamos;
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

	public JTable getTablaSocios() {
		return tablaSocios;
	}

	public void setTablaSocios(JTable tablaSocios) {
		this.tablaSocios = tablaSocios;
	}

	public DefaultTableModel getModelTablaSocios() {
		return modelTablaSocios;
	}

	public void setModelTablaSocios(DefaultTableModel modelTablaSocios) {
		this.modelTablaSocios = modelTablaSocios;
	}

	public JButton getbGuardaSocio() {
		return bGuardaSocio;
	}

	public void setbGuardaSocio(JButton bGuardaSocio) {
		this.bGuardaSocio = bGuardaSocio;
	}

	public JButton getbBorraSocio() {
		return bBorraSocio;
	}

	public void setbBorraSocio(JButton bBorraSocio) {
		this.bBorraSocio = bBorraSocio;
	}

	public JButton getbLimpiaSocio() {
		return bLimpiaSocio;
	}

	public void setbLimpiaSocio(JButton bLimpiaSocio) {
		this.bLimpiaSocio = bLimpiaSocio;
	}
	
	

}
