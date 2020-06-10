package prBiblioteca;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controlador extends WindowAdapter implements ActionListener, ListSelectionListener, MouseListener {

	private Vista miVista;
	private Modelo miModelo;
	
	public Controlador(Vista v) {
		
		miVista = v;
		miModelo = new Modelo(v.getConexion());
		
	}
	
	//Metodo de accion de los botones
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton bp = (JButton) e.getSource();
		String msg ="";
		
		try {
			
		if (bp == miVista.getbGuardaLibro())
			msg = guardaLibro();
		
		else if (bp == miVista.getbBorraLibro())
			msg = this.borraLibro();
		
		else if (bp == miVista.getbLimpiar())
			msg = this.limpia();
		
		else if (bp == miVista.getbGuardaSocio())
			msg = this.creaSocio();
		
		else if (bp == miVista.getbBorraSocio())
			msg = this.borraSocio();
		
		else if (bp == miVista.getbLimpiaSocio())
			msg = this.limpiaSocio();

		else if (bp == miVista.getPrestar()) 
			msg = this.prestaLibro();
		
		else if (bp == miVista.getDevolver())
			msg = this.devolverLibro();
		
		
		JOptionPane.showMessageDialog(miVista, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
		
		
		} catch (SQLException e1) {
			e1.printStackTrace();
			}
		
	}
	
	//Metodo de las listas y tablas (seleccionables)
	@Override
	public void valueChanged(ListSelectionEvent e) {
				
			try {
				
				if (e.getSource()==miVista.getDisponibles())
					this.cogeDatosLibro();
				
				else if (e.getSource()==miVista.getTablaSocios().getSelectionModel())
					this.cogeDatosSocio();
				
			}catch (NullPointerException | ArrayIndexOutOfBoundsException err) {
				//Si el error llegó hasta aquí no se captura, puesto que fue por la limpieza que deja el puntero a nulo o a -1
				//a la hora de no tener seleccionado ningun elemento de la lista o tabla
			} 
			
			catch (MalformedURLException e1) {
				miVista.getImagen().setIcon(null);
			}
			
		}
	
	//Métodos de raton
	@Override
	public void mouseClicked(MouseEvent e) {
		
		JPanel pp = (JPanel) e.getSource();
		
		miVista.getPortada().setVisible(false);
		
		//Se muestra visible el panel seleccionado por el menu lateral
		
		if (pp == miVista.getBtn_panel_libro()) {
		miVista.getPanelLibros().setVisible(true);
		miVista.getPanelSocios().setVisible(false);
		miVista.getPanelPrestamos().setVisible(false);

		}
		
		else if (pp == miVista.getBtn_panel_socios()) {			
		miVista.getPanelLibros().setVisible(false);
		miVista.getPanelSocios().setVisible(true);
		miVista.getPanelPrestamos().setVisible(false);

		}
		
		else {
		miVista.getPanelLibros().setVisible(false);
		miVista.getPanelSocios().setVisible(false);
		miVista.getPanelPrestamos().setVisible(true);

		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.getComponent().setBackground(new Color(38,38,38));
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.getComponent().setBackground((new Color(89, 89, 89)));
	}
	
		//Estos no están programados
	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	
	//Método para cerrar conexion y ventana
	@Override
	public void windowClosing(WindowEvent e) {
		
		try {
			miModelo.cierraStatement();
			miModelo.cierraConexion();
		} catch (SQLException e1) {}
		
		System.exit(0);
		
	}
	
	
	/**
	 * Métodos de utilidad privados que llaman a miModelo
	 * 
	 */
	
	//Correspondientes al panel de libros
	private void cogeDatosLibro() throws MalformedURLException {
		Libro l = miVista.getDisponibles().getSelectedValue();
		
		miVista.getCodAutor().setText(String.valueOf(l.getCodAutor()));
		miVista.getNombreAutor().setText(l.getAutor());
		miVista.getNacionalidad().setText(l.getNacionalidad());
		miVista.getCodLibro().setText(String.valueOf(l.getCodLibro()));
		miVista.getTitulo().setText(l.getTitulo());
		miVista.getAño().setText(l.getPublicacion());
		miVista.getImagen().setIcon(new ImageIcon(new URL(l.getImagen())));
		miVista.getUrl().setText(l.getImagen());
		miVista.getbGuardaLibro().setEnabled(false);
		miVista.getbBorraLibro().setEnabled(true);
	}
	
	private String guardaLibro() throws SQLException {
		String msg = "Se ha producido un error intentelo de nuevo";
		
		try {
			Libro l = miModelo.crearLibro(miVista.getNombreAutor().getText(), 
					miVista.getNacionalidad().getText(), 
					miVista.getTitulo().getText(), 
					miVista.getAño().getText(),
					miVista.getUrl().getText());

			msg = "LIBRO: "+l.getTitulo()+"\nDEL AUTOR: "+l.getAutor()+"\nDEL AÑO: "+l.getPublicacion()+"\nGUARDADO";

			this.limpia();

			miVista.getModelDisponibles().addElement(l);
			return msg;

		} catch (ExcepcionBiblioteca e) {
			JOptionPane.showMessageDialog(miVista,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			return msg;
		}		

	}
	
	private String borraLibro(){

		Libro l = miVista.getDisponibles().getSelectedValue();
		String msg ="";

		try {

			msg = miModelo.borrarLibro(l);
			miVista.getModelDisponibles().removeElement(l);
			this.limpia();
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(miVista,"No se puede borrar un libro que se encuentra prestado.","Error",JOptionPane.ERROR_MESSAGE);
			msg = this.limpia();
		}
		
		return msg;

	}
	
	private String limpia() {
		miVista.getCodAutor().setText("");
		miVista.getNombreAutor().setText("");
		miVista.getNacionalidad().setText("");
		miVista.getCodLibro().setText("");
		miVista.getTitulo().setText("");
		miVista.getAño().setText("");
		miVista.getUrl().setText("");
		miVista.getbGuardaLibro().setEnabled(true);
		miVista.getbBorraLibro().setEnabled(false);
		miVista.getDisponibles().clearSelection();
		miVista.getImagen().setIcon(new ImageIcon("./imagenes/libro.jpg"));
		
		return "Campos del formulario limpiados";
	}
	
	//Correspondientes al panel de socios
	private void cogeDatosSocio() {
		int fila = miVista.getTablaSocios().getSelectedRow();
		
		miVista.getCodSocio().setText(String.valueOf(miVista.getModelTablaSocios().getValueAt(fila, 0)));
		miVista.getNombreSocio().setText(String.valueOf(miVista.getModelTablaSocios().getValueAt(fila, 1)));
		miVista.getApellidos().setText(String.valueOf(miVista.getModelTablaSocios().getValueAt(fila, 2)));
		miVista.getTelefono().setText(String.valueOf(miVista.getModelTablaSocios().getValueAt(fila, 3)));
		miVista.getDireccion().setText(String.valueOf(miVista.getModelTablaSocios().getValueAt(fila, 4)));
		miVista.getbBorraSocio().setEnabled(true);
		miVista.getbGuardaSocio().setText("Editar");
	}
	
	private String creaSocio() throws SQLException {
		
		String msg = "Se ha producido un error intentelo de nuevo.";
		
		try {
			
			int codigo = miVista.getCodSocio().getText().isEmpty()?-1:Integer.parseInt(miVista.getCodSocio().getText());
			
			Socio s = miModelo.creaSocio(codigo,
										miVista.getNombreSocio().getText(),
										miVista.getApellidos().getText(),
										miVista.getTelefono().getText(),
										miVista.getDireccion().getText());
			
			if (codigo!=-1)
				miVista.getModelTablaSocios().removeRow(miVista.getTablaSocios().getSelectedRow());
				
			
			miVista.getModelTablaSocios().addRow(new Object[] {s.getCodSocio(),s.getNombre(),s.getApellidos(),s.getTelefono(),s.getDireccion()});
			this.limpiaSocio();
			
			return "Se ha realizado la operacion correctamente";
			
		}catch(ExcepcionBiblioteca e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			return msg;
		}


		
	}
	
	private String borraSocio() {
		String msg="";
		
		try {
			int fila = miVista.getTablaSocios().getSelectedRow();
			
			Socio s = new Socio((Integer)miVista.getModelTablaSocios().getValueAt(fila, 0),
								(String)miVista.getModelTablaSocios().getValueAt(fila, 1),
								(String)miVista.getModelTablaSocios().getValueAt(fila, 2),
								(String)miVista.getModelTablaSocios().getValueAt(fila, 3),
								(String)miVista.getModelTablaSocios().getValueAt(fila, 4));
			
			msg = miModelo.borrarSocio(s.getCodSocio());
			miVista.getModelTablaSocios().removeRow(fila);
			this.limpiaSocio();
		
		} catch (NumberFormatException | SQLException e) {
			JOptionPane.showMessageDialog(miVista, "No se puede borrar un socio que tiene un libro en prestamo.","Error",JOptionPane.ERROR_MESSAGE);
			msg = this.limpiaSocio();
		}
		
		return msg;
	}
	
	private String limpiaSocio() {
		
		miVista.getCodSocio().setText(null);
		miVista.getNombreSocio().setText(null);
		miVista.getApellidos().setText(null);
		miVista.getTelefono().setText(null);
		miVista.getDireccion().setText(null);
		miVista.getTablaSocios().clearSelection();
		miVista.getbBorraSocio().setEnabled(false);
		miVista.getbGuardaSocio().setText("Guardar");
		
		return "Campos de socio limpiados";
		
	}
	
	//Correspondientes al panel de prestamos
	private String prestaLibro() throws SQLException {
		
		try {
			
			int fila = miVista.getTableSocio().getSelectedRow();
			
			Socio s = new Socio((Integer)miVista.getModelTablaSocios().getValueAt(fila,0),
								(String)miVista.getModelTablaSocios().getValueAt(fila,1),
								(String)miVista.getModelTablaSocios().getValueAt(fila,2),
								(String)miVista.getModelTablaSocios().getValueAt(fila,3),
								(String)miVista.getModelTablaSocios().getValueAt(fila,4));
			
			
			Libro l = miVista.getListaLibros().getSelectedValue();
			
			Prestamo p = miModelo.prestarLibro(l,s);
			
			miVista.getModelTablaPrestamos().addRow(new Object[] {p.getCodPrestamo(),p.getTitulo(),p.getNombre(),p.getApellido(),p.getFecha(),p.getDevolucion()});
			miVista.getTableSocio().clearSelection();
			miVista.getListaLibros().clearSelection();
			
			return "LIBRO PRESTADO: "+l.getTitulo()+
					" \nAL SOCIO: "+s.getNombre()+" "+s.getApellidos()+
					" \nFECHA PRESTAMO: "+p.getFecha()+
					" \nFECHA DEVOLUCIÓN: "+p.getDevolucion();
			
		}catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			JOptionPane.showMessageDialog(miVista, "Asegurese de seleccionar un socio y un libro", "Error", JOptionPane.ERROR_MESSAGE);
			return "Se ha producido un error, intentelo de nuevo";
		}
		
	}
	
	private String devolverLibro() throws SQLException {
		
		try {
			
			int fila = miVista.getTablePrestamos().getSelectedRow();
			
			Prestamo p = new Prestamo ((Integer)miVista.getModelTablaPrestamos().getValueAt(fila,0),
									   (String)miVista.getModelTablaPrestamos().getValueAt(fila,1),
									   (String)miVista.getModelTablaPrestamos().getValueAt(fila,2),
									   (String)miVista.getModelTablaPrestamos().getValueAt(fila,3),
									   (String)miVista.getModelTablaPrestamos().getValueAt(fila,4),
									   (String)miVista.getModelTablaPrestamos().getValueAt(fila,5));
			
			miVista.getModelTablaPrestamos().removeRow(miVista.getTablePrestamos().getSelectedRow());
			
			return miModelo.devolverLibro(p);
			
		} catch (ArrayIndexOutOfBoundsException e) {}
		JOptionPane.showMessageDialog(miVista, "Asegurese de seleccionar un libro a devolver", "Error", JOptionPane.ERROR_MESSAGE);
		return "Se ha producido un error, intentelo de nuevo";
	}

	
	//Getters y setters
	public Vista getMiVista() {
		return miVista;
	}

	public void setMiVista(Vista miVista) {
		this.miVista = miVista;
	}

	public Modelo getMiModelo() {
		return miModelo;
	}

	public void setMiModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

}
