package prBiblioteca;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controlador implements ActionListener, ListSelectionListener {

	private Vista miVista;
	private Modelo miModelo;
	
	public Controlador(Vista v) {
		
		miVista = v;
		miModelo = new Modelo(v.getConexion());
		
	}
	
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
		
		
		JOptionPane.showMessageDialog(miVista, msg, "Informaci�n", JOptionPane.INFORMATION_MESSAGE);
		
		
		} catch (SQLException e1) {
			e1.printStackTrace();
			}
		
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
				
			try {
				
				if (e.getSource()==miVista.getDisponibles())
					this.cogeDatosLibro();
				
				else if (e.getSource()==miVista.getTablaSocios().getSelectionModel())
					this.cogeDatosSocio();
				
			}catch (NullPointerException | ArrayIndexOutOfBoundsException err) {
				//Si el error lleg� hasta aqu� no se captura, puesto que fue por la limpieza que deja el puntero a nulo o a -1
				//a la hora de no tener seleccionado ningun elemento de la lista o tabla
			} 
			
			catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
	
	private void cogeDatosLibro() throws MalformedURLException {
		Libro l = miVista.getDisponibles().getSelectedValue();
		
		miVista.getCodAutor().setText(String.valueOf(l.getCodAutor()));
		miVista.getNombreAutor().setText(l.getAutor());
		miVista.getNacionalidad().setText(l.getNacionalidad());
		miVista.getCodLibro().setText(String.valueOf(l.getCodLibro()));
		miVista.getTitulo().setText(l.getTitulo());
		miVista.getA�o().setText(l.getPublicacion());
		miVista.getImagen().setIcon(new ImageIcon(new URL(l.getImagen())));
		miVista.getUrl().setText(l.getImagen());
		miVista.getbGuardaLibro().setEnabled(false);
		miVista.getbBorraLibro().setEnabled(true);
	}
	
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

	private String guardaLibro() throws SQLException {
		String msg;
		
		Libro l = miModelo.crearLibro(miVista.getNombreAutor().getText(), 
										miVista.getNacionalidad().getText(), 
										miVista.getTitulo().getText(), 
										miVista.getA�o().getText(),
										miVista.getUrl().getText());
		
		msg = "LIBRO: "+l.getTitulo()+"\nDEL AUTOR: "+l.getAutor()+"\nDEL A�O: "+l.getPublicacion()+"\nGUARDADO";
		
		this.limpia();
				
		miVista.getModelDisponibles().addElement(l);
	
		return msg;
	}
	
	private String borraLibro(){

		Libro l = miVista.getDisponibles().getSelectedValue();
		String msg ="";

		try {

			msg = miModelo.borrarLibro(l);
			miVista.getModelDisponibles().removeElement(l);
			this.limpia();
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(miVista, "No se puede borrar un libro que se encuentra prestado.","Error",JOptionPane.ERROR_MESSAGE);
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
		miVista.getA�o().setText("");
		miVista.getUrl().setText("");
		miVista.getbGuardaLibro().setEnabled(true);
		miVista.getbBorraLibro().setEnabled(false);
		miVista.getDisponibles().clearSelection();
		
		return "Campos del formulario limpiados";
	}
	
	private String creaSocio() throws SQLException {

		int codigo = miVista.getCodSocio().getText().isEmpty()?-1:Integer.parseInt(miVista.getCodSocio().getText());
		
		Socio s = miModelo.creaSocio(codigo,
									miVista.getNombreSocio().getText(),
									miVista.getApellidos().getText(),
									miVista.getTelefono().getText(),
									miVista.getDireccion().getText());
		
		if (codigo!=-1)
			miVista.getModelTablaSocios().removeRow(miVista.getTablaSocios().getSelectedRow());
			
		
		miVista.getModelTablaSocios().addRow(new Object[] {s.getCodSocio(),s.getNombre(),s.getApellidos(),s.getTelefono(),s.getDireccion()});
		miVista.getModelSocios().addElement(s);
		this.limpiaSocio();
		
		return "Se ha editado o creado un socio";
		
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
			miVista.getModelSocios().removeElement(s);
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
	
	private String prestaLibro() throws SQLException {
		Libro l = miVista.getLibros().getSelectedValue();
		Socio s = miVista.getSocios().getSelectedValue();
		Prestamo p = miModelo.prestarLibro(l,s);
		
		String msg = "LIBRO PRESTADO: "+l.getTitulo()+
					" \nAL SOCIO: "+s.getNombre()+" "+s.getApellidos()+
					" \nFECHA PRESTAMO: "+p.getFecha()+
					" \nFECHA DEVOLUCI�N: "+p.getDevolucion();		
		

		miVista.getModelPrestamos().addElement(p);
		
		return msg;
	}
	
	private String devolverLibro() throws SQLException {
		Prestamo p = miVista.getPrestamos().getSelectedValue();
		String msg = miModelo.devolverLibro(p);
		miVista.getModelPrestamos().removeElement(p);
		
		return msg;
	}

	

}
