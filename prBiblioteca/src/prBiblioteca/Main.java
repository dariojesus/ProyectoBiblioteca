package prBiblioteca;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

@SuppressWarnings("unused")
public class Main {
	
	public static final String nombre = "mibase";

	public static void main(String[] args) {
		
			try {
				
				UIManager.setLookAndFeel(new FlatLightLaf());
				
				//Estas sentencias deben ejecutarse SOLO LA PRIMERA VEZ que se inicia el programa para crear y rellenar las tablas
//				Connection conexion = LibreriaBaseDatos.conecta(nombre, "dariojesus", "1234");
//				LibreriaBaseDatos.creaTablas(conexion, nombre);
//				LibreriaBaseDatos.rellenaTablas(conexion, nombre);
//				conexion.close();
				//
				
				Vista miVista = new Vista();
				Controlador c = new Controlador(miVista);
				miVista.control(c);
				
				JFrame ventana = new JFrame();
				ventana.setLocationRelativeTo(null);
				ventana.setIconImage(null);
				ventana.setBackground(new Color(255, 255, 255));
				ventana.setPreferredSize(new Dimension(988, 619));
				ventana.setContentPane(miVista);
				ventana.setResizable(false);
				ventana.addWindowListener(c);
				ventana.pack();
				ventana.setVisible(true);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			

	}
	public static void printSQLException(SQLException ex)
	{
		ex.printStackTrace(System.err);
		System.err.println("SQLState: "+ex.getSQLState());
		System.err.println("Error code: "+ex.getErrorCode());
		System.err.println("Message: "+ex.getMessage());
		Throwable t = ex.getCause();
		while (t!=null) {
			System.out.println("Cause: "+t);
			t = t.getCause();
		}
	}

}
