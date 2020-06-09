package prBiblioteca;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;


public class Vista extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Connection conexion = null;
	
	//Campos accesibles del panel principal (menu lateral)
	private JPanel btn_panel_libro;
	private JPanel btn_panel_socios;
	private JPanel btn_panel_prestamos;
	private JPanel panelLibros;
	private JPanel panelSocios;
	private JPanel panelPrestamos;
	private JPanel portada;
	
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
	private JTable tableSocio;
	private JTable tablePrestamos;
	private DefaultTableModel modelTablaPrestamos;
	private JList<Libro> listaLibros;
	private JButton prestar;
	private JButton devolver;
	
	public Vista() throws SQLException, MalformedURLException {
				
		//Dialogo de inicio con la conexion
		DialogoLogin d = new DialogoLogin();
		conexion = d.getConexion();
		
		
		//Configuraciones iniciales del panel de fondo
		this.setBackground(new Color(255, 255, 255));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		
		
		//Creamos el panel principal que nos hará de menú para la interfaz.
		JPanel principal = panelMenu();
		portada = panelPortada();
		
		panelLibros = panelLibro();
		panelLibros.setVisible(false);
		
		panelSocios = panelSocio();
		panelSocios.setVisible(false);
		
		panelPrestamos = panelPrestamo();
		panelPrestamos.setVisible(false);
		
		
		this.add(principal);
		this.add(portada);
		this.add(panelLibros);
		this.add(panelSocios);
		this.add(panelPrestamos);
		
	}
	
	//Panel con imagen de la portada
	private JPanel panelPortada() {
		JPanel p = new JPanel();
		p.setBorder(null);
		p.setBackground(new Color(255, 255, 255));
		p.setBounds(231, 0, 743, 583);
		p.setLayout(null);
		
		JLabel portada = new JLabel("");
		portada.setIcon(new ImageIcon("./imagenes/biblio.jpg"));
		portada.setBounds(0, 0, 785, 607);
		p.add(portada);
		
		return p;
	}
	
	private JPanel panelMenu() {
		
		
		JPanel lateral = new JPanel();
		lateral.setBackground(new Color(89, 89, 89));
		lateral.setBounds(0, 0, 233, 602);
		lateral.setLayout(null);
			
			//Cabecera del panel lateral
			JLabel ltitulo1 = new JLabel("Sistema de Gestión");
			ltitulo1.setForeground(new Color(255, 255, 255));
			ltitulo1.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
			ltitulo1.setBounds(10, 28, 217, 56);
			lateral.add(ltitulo1);
				
			JLabel ltitulo2 = new JLabel("Biblioteca");
			ltitulo2.setForeground(new Color(255, 255, 255));
			ltitulo2.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
			ltitulo2.setBounds(10, 56, 217, 56);
			lateral.add(ltitulo2);
			
			JSeparator separator = new JSeparator();
			separator.setBounds(0, 171, 187, 9);
			lateral.add(separator);
			
			//Botón de libros lateral
			btn_panel_libro = new JPanel();
			btn_panel_libro.setBackground(new Color(89, 89, 89));
			btn_panel_libro.setBounds(0, 207, 232, 56);
			lateral.add(btn_panel_libro);
			btn_panel_libro.setLayout(new BoxLayout(btn_panel_libro, BoxLayout.X_AXIS));
				
				Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
				btn_panel_libro.add(rigidArea);
					
				JLabel icon1 = new JLabel("      ");
				icon1.setIcon(new ImageIcon("./imagenes/books.png"));
				icon1.setAlignmentX(0.5f);
				btn_panel_libro.add(icon1);
					
				JLabel label1 = new JLabel("Gestión de libros");
				label1.setForeground(new Color(255, 255, 255));
				label1.setFont(new Font("Segoe UI", Font.BOLD, 12));
				label1.setAlignmentX(0.5f);
				btn_panel_libro.add(label1);
				
				
			//Boton de socios lateral
			btn_panel_socios = new JPanel();
			btn_panel_socios.setBackground(new Color(89, 89, 89));
			btn_panel_socios.setBounds(0, 271, 232, 56);
			lateral.add(btn_panel_socios);
			btn_panel_socios.setLayout(new BoxLayout(btn_panel_socios, BoxLayout.X_AXIS));
				
				Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
				btn_panel_socios.add(rigidArea_1);
					
				JLabel icon2 = new JLabel("     ");
				icon2.setIcon(new ImageIcon("./imagenes/reading.png"));
				icon2.setAlignmentX(0.5f);
				btn_panel_socios.add(icon2);
					
				JLabel label2 = new JLabel("Administración de socios");
				label2.setForeground(new Color(255, 255, 255));
				label2.setFont(new Font("Segoe UI", Font.BOLD, 12));
				label2.setAlignmentX(0.5f);
				btn_panel_socios.add(label2);
				
			//Boton de prestamos lateral
			btn_panel_prestamos = new JPanel();
			btn_panel_prestamos.setBackground(new Color(89, 89, 89));
			btn_panel_prestamos.setBounds(0, 337, 232, 56);
			lateral.add(btn_panel_prestamos);
			btn_panel_prestamos.setLayout(new BoxLayout(btn_panel_prestamos, BoxLayout.X_AXIS));
				
				Component rigidArea_1_1 = Box.createRigidArea(new Dimension(20, 20));
				btn_panel_prestamos.add(rigidArea_1_1);
					
				JLabel icon3 = new JLabel("    ");
				icon3.setIcon(new ImageIcon("./imagenes/ca.png"));
				icon3.setAlignmentX(0.5f);
				btn_panel_prestamos.add(icon3);
					
				JLabel label3 = new JLabel("Prestamos y devoluciones");
				label3.setForeground(new Color(255, 255, 255));
				label3.setFont(new Font("Segoe UI", Font.BOLD, 12));
				label3.setAlignmentX(0.5f);
				btn_panel_prestamos.add(label3);
		
		return lateral;
		
	}
	
	private JPanel panelLibro() throws SQLException, MalformedURLException {
		
		//Creamos los paneles y subpaneles del panel de opciones libro
		JPanel p = new JPanel();
		p.setBorder(null);
		p.setBackground(new Color(255, 255, 255));
		p.setBounds(255, 0, 709, 583);
		p.setLayout(null);
		
		JPanel datos = new JPanel();
		datos.setBorder(null);
		datos.setBackground(new Color(255, 255, 255));
		datos.setBounds(376, 49, 286, 356);
		p.add(datos);
		datos.setLayout(null);
		
		
			//Añadimos los campos de texto
			codAutor = new JTextField();
			codAutor.setEditable(false);
			codAutor.setBackground(new Color(242, 242, 242));
			codAutor.setBorder(null);
			codAutor.setBounds(134, 19, 128, 19);
			datos.add(codAutor);
			codAutor.setColumns(10);
			
			nombreAutor = new JTextField();
			nombreAutor.setBackground(new Color(242, 242, 242));
			nombreAutor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			nombreAutor.setColumns(10);
			nombreAutor.setBounds(134, 58, 128, 19);
			datos.add(nombreAutor);
			
			nacionalidad = new JTextField();
			nacionalidad.setBackground(new Color(242, 242, 242));
			nacionalidad.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			nacionalidad.setColumns(10);
			nacionalidad.setBounds(134, 98, 128, 19);
			datos.add(nacionalidad);
			
			codLibro = new JTextField();
			codLibro.setEditable(false);
			codLibro.setBackground(new Color(242, 242, 242));
			codLibro.setBorder(null);
			codLibro.setColumns(10);
			codLibro.setBounds(134, 141, 128, 19);
			datos.add(codLibro);
			
			titulo = new JTextField();
			titulo.setBackground(new Color(242, 242, 242));
			titulo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			titulo.setColumns(10);
			titulo.setBounds(134, 185, 128, 19);
			datos.add(titulo);
			
			año = new JTextField();
			año.setColumns(10);
			año.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			año.setBackground(new Color(242, 242, 242));
			año.setBounds(134, 228, 128, 19);
			datos.add(año);
			
			url = new JTextArea();
			url.setColumns(10);
			url.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			url.setBackground(new Color(242, 242, 242));
			url.setBounds(134, 274, 128, 19);
			datos.add(url);
		
			
			//Añadimos las etiquetas
			JLabel lautor = new JLabel("Código autor:");
			lautor.setBounds(10, 25, 101, 13);
			datos.add(lautor);
			
			JLabel lnombre = new JLabel("Nombre autor:");
			lnombre.setBounds(10, 64, 101, 13);
			datos.add(lnombre);
			
			JLabel lnacion = new JLabel("Nacionalidad:");
			lnacion.setBounds(10, 104, 101, 13);
			datos.add(lnacion);
			
			JLabel llibro = new JLabel("Código libro:");
			llibro.setBounds(10, 147, 101, 13);
			datos.add(llibro);
			
			JLabel ltitulo = new JLabel("Titulo:");
			ltitulo.setBounds(10, 191, 101, 13);
			datos.add(ltitulo);
			
			JLabel laño = new JLabel("Año publicación:");
			laño.setBounds(10, 234, 101, 13);
			datos.add(laño);
			
			JLabel limagen = new JLabel("Url imagen:");
			limagen.setBounds(10, 280, 101, 13);
			datos.add(limagen);
			
			
			//Añadimos los botones
			bGuardaLibro = new JButton("Guardar");
			bGuardaLibro.setBackground(new Color(166, 166, 166));
			bGuardaLibro.setBorder(null);
			bGuardaLibro.setBounds(0, 325, 85, 21);
			datos.add(bGuardaLibro);
			
			bBorraLibro = new JButton("Borrar");
			bBorraLibro.setBackground(new Color(166, 166, 166));
			bBorraLibro.setBorder(null);
			bBorraLibro.setBounds(95, 325, 96, 21);
			bBorraLibro.setEnabled(false);
			datos.add(bBorraLibro);
			
			bLimpiar = new JButton("Limpiar");
			bLimpiar.setBackground(new Color(166, 166, 166));
			bLimpiar.setBorder(null);
			bLimpiar.setActionCommand("bLimpiar");
			bLimpiar.setBounds(201, 325, 85, 21);
			datos.add(bLimpiar);

			
		//Creamos el scroll pane para las imagenes de las portadas
		JScrollPane scrollImagen = new JScrollPane();
		scrollImagen.setBounds(10, 49, 326, 500);
		p.add(scrollImagen);
		scrollImagen.setBackground(new Color(255, 255, 255));
		scrollImagen.setBorder(null);
				
		imagen = new JLabel("");
		scrollImagen.setViewportView(imagen);
		imagen.setIcon(new ImageIcon("./imagenes/libro.jpg"));			
	
		
		//Creamos un modelo de libros para mostrar en los libros disponibles y su scrollPane
		modelDisponibles.addAll(this.listarLibros());
		disponibles = new JList<Libro>();
		disponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		disponibles.setSelectionBackground(new Color(242, 159, 5));
		disponibles.setBackground(new Color(255, 255, 255));
		disponibles.setModel(modelDisponibles);
		
		JScrollPane scrollDisponibles = new JScrollPane();
		scrollDisponibles.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollDisponibles.setBorder(null);
		scrollDisponibles.setBounds(376, 406, 286, 143);
		scrollDisponibles.setViewportView(disponibles);
		p.add(scrollDisponibles);
		
							
		return p;
		
	}

	private JPanel panelSocio() throws SQLException {
		JPanel p = new JPanel();
		p.setBorder(null);
		p.setBackground(new Color(255, 255, 255));
		p.setBounds(243, 0, 731, 583);
		p.setLayout(null);
		
			//Creamos el panel con los campos de los datos de los usuarios
			JPanel Campos = new JPanel();
			Campos.setBounds(0, 43, 236, 191);
			Campos.setBackground(new Color(255, 255, 255));
			p.add(Campos);
			Campos.setLayout(null);
			
				//Creamos las cajas de texto
				codSocio = new JTextField();
				codSocio.setBounds(101, 30, 96, 19);
				codSocio.setBackground(new Color(242, 242, 242));
				codSocio.setBorder(null);
				codSocio.setColumns(10);
				codSocio.setEditable(false);
				Campos.add(codSocio);

				
				nombreSocio = new JTextField();
				nombreSocio.setBounds(101, 59, 96, 19);
				nombreSocio.setBackground(new Color(242, 242, 242));
				nombreSocio.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				nombreSocio.setColumns(10);
				Campos.add(nombreSocio);

				
				apellidos = new JTextField();
				apellidos.setBounds(101, 88, 96, 19);
				apellidos.setBackground(new Color(242, 242, 242));
				apellidos.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				apellidos.setColumns(10);
				Campos.add(apellidos);
				
				
				telefono = new JTextField();
				telefono.setBounds(101, 117, 96, 19);
				telefono.setBackground(new Color(242, 242, 242));
				telefono.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				telefono.setColumns(10);
				Campos.add(telefono);
		
				
				direccion = new JTextField();
				direccion.setBounds(101, 146, 96, 19);
				direccion.setBackground(new Color(242, 242, 242));
				direccion.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				direccion.setColumns(10);
				Campos.add(direccion);
				
				
				//Creamos las etiquetas
				JLabel lcodSocio = new JLabel("Codigo socio: ");
				lcodSocio.setBounds(10, 33, 93, 13);
				Campos.add(lcodSocio);
				
				JLabel lnombreSocio = new JLabel("Nombre: ");
				lnombreSocio.setBounds(10, 62, 93, 13);
				Campos.add(lnombreSocio);
				
				JLabel lApellidos = new JLabel("Apellidos: ");
				lApellidos.setBounds(10, 91, 93, 13);
				Campos.add(lApellidos);
				
				JLabel lTelefono = new JLabel("Teléfono: ");
				lTelefono.setBounds(10, 120, 93, 13);
				Campos.add(lTelefono);
				
				JLabel lDirección = new JLabel("Dirección: ");
				lDirección.setBounds(10, 149, 93, 13);
				Campos.add(lDirección);
				
				//Añadimos los botones
				bGuardaSocio = new JButton("Guardar");			
				bGuardaSocio.setBounds(0, 244, 81, 21);
				bGuardaSocio.setBackground(new Color(166, 166, 166));
				bGuardaSocio.setBorder(null);
				p.add(bGuardaSocio);
				
				bBorraSocio = new JButton("Borrar");
				bBorraSocio.setBounds(84, 244, 81, 21);
				bBorraSocio.setBackground(new Color(166, 166, 166));
				bBorraSocio.setBorder(null);
				bBorraSocio.setEnabled(false);
				p.add(bBorraSocio);
				
				bLimpiaSocio = new JButton("Limpiar");
				bLimpiaSocio.setBounds(168, 244, 81, 21);
				bLimpiaSocio.setBackground(new Color(166, 166, 166));
				bLimpiaSocio.setBorder(null);
				p.add(bLimpiaSocio);
				
			
		//Se crea el scrollpane y la tabla para mostrar la informacion de la base de datos
		JScrollPane scrollSocios = new JScrollPane();
		scrollSocios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollSocios.setBounds(256, 43, 453, 520);
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
		tablaSocios.setShowVerticalLines(false);
		tablaSocios.setSelectionBackground(new Color(242,159,5));
		tablaSocios.setBorder(null);
		tablaSocios.setFillsViewportHeight(true);
		tablaSocios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaSocios.setBackground(Color.WHITE);
		
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
		p.setBorder(null);
		p.setBackground(new Color(255, 255, 255));
		p.setBounds(255, 0, 709, 583);
		p.setLayout(null);
		
		JSplitPane splitPrestar = new JSplitPane();
		splitPrestar.setContinuousLayout(true);
		splitPrestar.setBorder(null);
		splitPrestar.setBounds(25, 33, 631, 229);
		p.add(splitPrestar);
		
		JScrollPane scrollSocio = new JScrollPane();
		splitPrestar.setLeftComponent(scrollSocio);
		
		tableSocio = new JTable();
		tableSocio.setShowVerticalLines(false);
		tableSocio.setSelectionBackground(new Color(242,159,5));
		tableSocio.setBorder(null);
		tableSocio.setFillsViewportHeight(true);
		tableSocio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSocio.setBackground(Color.WHITE);
		tableSocio.setModel(modelTablaSocios);
		scrollSocio.setViewportView(tableSocio);
		
		JScrollPane scrollLibro = new JScrollPane();
		splitPrestar.setRightComponent(scrollLibro);
		
		listaLibros = new JList<Libro>();
		listaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaLibros.setSelectionBackground(new Color(242, 159, 5));
		listaLibros.setBackground(new Color(255, 255, 255));
		listaLibros.setModel(modelDisponibles);
		scrollLibro.setViewportView(listaLibros);
		
		JScrollPane scrollPrestamos = new JScrollPane();
		scrollPrestamos.setBounds(25, 308, 631, 229);
		p.add(scrollPrestamos);
		
		
			//Hacer modelo de la tabla prestamos
			modelTablaPrestamos = new DefaultTableModel();
			modelTablaPrestamos.addColumn("Código Prestamo");
			modelTablaPrestamos.addColumn("Libro");
			modelTablaPrestamos.addColumn("Nombre Socio");
			modelTablaPrestamos.addColumn("Apellidos Socio");
			modelTablaPrestamos.addColumn("Fecha prestamo");
			modelTablaPrestamos.addColumn("Fecha devolución");
			
			for (Prestamo pres : this.listarPrestamos())
				modelTablaPrestamos.addRow(new Object[] {pres.getCodPrestamo(),pres.getTitulo(),pres.getNombre(),pres.getApellido(),pres.getFecha(),pres.getDevolucion()});
		
		tablePrestamos = new JTable();
		tablePrestamos.setShowVerticalLines(false);
		tablePrestamos.setSelectionBackground(new Color(242,159,5));
		tablePrestamos.setBorder(null);
		tablePrestamos.setFillsViewportHeight(true);
		tablePrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePrestamos.setBackground(Color.WHITE);
		
		tablePrestamos.setModel(modelTablaPrestamos);
		scrollPrestamos.setViewportView(tablePrestamos);
		
		prestar = new JButton("Prestar");
		prestar.setBounds(23, 272, 85, 21);
		prestar.setBackground(new Color(166, 166, 166));
		prestar.setBorder(null);
		p.add(prestar);
		
		devolver = new JButton("Devolver");
		devolver.setBounds(25, 547, 85, 21);
		devolver.setBackground(new Color(166, 166, 166));
		devolver.setBorder(null);
		p.add(devolver);
		
			
		return p;
	}
	
	//Métodos útiles para formar las tablas y listas de inicio
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

	//Listeners de la vista
	public void control (Controlador ctr) {
		//Listeners del panel libro
		btn_panel_libro.addMouseListener(ctr);
		btn_panel_socios.addMouseListener(ctr);
		btn_panel_prestamos.addMouseListener(ctr);
		
		
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

	public JTable getTableSocio() {
		return tableSocio;
	}

	public void setTableSocio(JTable tableSocio) {
		this.tableSocio = tableSocio;
	}

	public JTable getTablePrestamos() {
		return tablePrestamos;
	}

	public void setTablePrestamos(JTable tablePrestamos) {
		this.tablePrestamos = tablePrestamos;
	}

	public DefaultTableModel getModelTablaPrestamos() {
		return modelTablaPrestamos;
	}

	public void setModelTablaPrestamos(DefaultTableModel modelTablaPrestamos) {
		this.modelTablaPrestamos = modelTablaPrestamos;
	}

	public JList<Libro> getListaLibros() {
		return listaLibros;
	}

	public void setListaLibros(JList<Libro> listaLibros) {
		this.listaLibros = listaLibros;
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

	public JPanel getBtn_panel_libro() {
		return btn_panel_libro;
	}

	public void setBtn_panel_libro(JPanel btn_panel_libro) {
		this.btn_panel_libro = btn_panel_libro;
	}

	public JPanel getPanelLibros() {
		return panelLibros;
	}

	public void setPanelLibros(JPanel panelLibros) {
		this.panelLibros = panelLibros;
	}

	public JPanel getBtn_panel_socios() {
		return btn_panel_socios;
	}

	public void setBtn_panel_socios(JPanel btn_panel_socios) {
		this.btn_panel_socios = btn_panel_socios;
	}

	public JPanel getBtn_panel_prestamos() {
		return btn_panel_prestamos;
	}

	public void setBtn_panel_prestamos(JPanel btn_panel_prestamos) {
		this.btn_panel_prestamos = btn_panel_prestamos;
	}

	public JPanel getPanelSocios() {
		return panelSocios;
	}

	public void setPanelSocios(JPanel panelSocios) {
		this.panelSocios = panelSocios;
	}

	public JPanel getPanelPrestamos() {
		return panelPrestamos;
	}

	public void setPanelPrestamos(JPanel panelPrestamos) {
		this.panelPrestamos = panelPrestamos;
	}

	public JPanel getPortada() {
		return portada;
	}

	public void setPortada(JPanel portada) {
		this.portada = portada;
	}
	
	
}
