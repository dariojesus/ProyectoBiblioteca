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

		else if (bp == miVista.getPrestar()) 
			msg = this.prestaLibro();
		
		else if (bp == miVista.getDevolver())
			msg = this.devolverLibro();
		
		
		JOptionPane.showMessageDialog(miVista, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
		
		
		} catch (SQLException e1) {
			e1.printStackTrace();
			}
		
	}
	
	private String guardaLibro() throws SQLException {
		String msg;
		
		Libro l = miModelo.crearLibro(miVista.getNombreAutor().getText(), 
										miVista.getNacionalidad().getText(), 
										miVista.getTitulo().getText(), 
										miVista.getAño().getText(),
										miVista.getUrl().getText());
		
		msg = "LIBRO: "+l.getTitulo()+"\nDEL AUTOR: "+l.getAutor()+"\nDEL AÑO: "+l.getPublicacion()+"\nGUARDADO";
		
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
		miVista.getAño().setText("");
		miVista.getUrl().setText("");
		miVista.getbGuardaLibro().setEnabled(true);
		miVista.getbBorraLibro().setEnabled(false);
		miVista.getDisponibles().clearSelection();
		
		return "Campos del formulario limpiados";
	}
	
	private String prestaLibro() throws SQLException {
		Libro l = miVista.getLibros().getSelectedValue();
		Socio s = miVista.getSocios().getSelectedValue();
		Prestamo p = miModelo.prestarLibro(l,s);
		
		String msg = "LIBRO PRESTADO: "+l.getTitulo()+
					" \nAL SOCIO: "+s.getNombre()+" "+s.getApellidos()+
					" \nFECHA PRESTAMO: "+p.getFecha()+
					" \nFECHA DEVOLUCIÓN: "+p.getDevolucion();		
		

		miVista.getModelPrestamos().addElement(p);
		
		return msg;
	}
	
	private String devolverLibro() throws SQLException {
		Prestamo p = miVista.getPrestamos().getSelectedValue();
		String msg = miModelo.devolverLibro(p);
		miVista.getModelPrestamos().removeElement(p);
		
		return msg;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		if (e.getSource()==miVista.getDisponibles()) {
			
			try {
				
				Libro l = miVista.getDisponibles().getSelectedValue();
				
				
				miVista.getCodAutor().setText(String.valueOf(l.getCodAutor()));
				miVista.getNombreAutor().setText(l.getAutor());
				miVista.getNacionalidad().setText(l.getNacionalidad());
				miVista.getCodLibro().setText(String.valueOf(l.getCodLibro()));
				miVista.getTitulo().setText(l.getTitulo());
				miVista.getAño().setText(l.getPublicacion());
				miVista.getImagen().setIcon(new ImageIcon(new URL(l.getImagen())));
				
				miVista.getbGuardaLibro().setEnabled(false);
				miVista.getbBorraLibro().setEnabled(true);
				
			}catch (NullPointerException err) {} 
			
			catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
	}

}
