package prBiblioteca;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class DialogoLogin extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final String bd = "mibase";
	private static final long serialVersionUID = 1L;
	private JTextField nombre;
	private JPasswordField contraseña;
	private JButton login,cancelar;
	private Connection conexion = null;
	
	public DialogoLogin() {
		this.setModal(true);
		this.setTitle("Login gestión biblioteca");
		this.setLayout(new BorderLayout(50,50));
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		
		JPanel principal = new JPanel();
		principal.setLayout(new BorderLayout());
		
			//Preparación del panel compuesto de autentificación
			JPanel interno = new JPanel();
			interno.setLayout(new GridLayout(2,2));
			interno.setBorder(new TitledBorder("Autentificación"));
			
				//Preparación de la etiqueta y panel para el nombre
				JLabel nom = new JLabel("Nombre: ");
				nom.setHorizontalAlignment(SwingConstants.RIGHT);
				interno.add(nom);
			
				JPanel pNombre = new JPanel();
				pNombre.setLayout(new FlowLayout());
				nombre = new JTextField(8);
				nombre.setText("dariojesus");
				
				pNombre.add(nombre);
				interno.add(pNombre);
				
				
				//Preparación de la etiqueta y panel para contraseña
				JLabel con = new JLabel("Contraseña: ");
				con.setHorizontalAlignment(SwingConstants.RIGHT);
				interno.add(con);
				
				JPanel pContra = new JPanel();
				pContra.setLayout(new FlowLayout());
				contraseña = new JPasswordField(8);
				contraseña.setText("1234");
				
				pContra.add(contraseña);
				interno.add(pContra);
				
			//Preparación del panel inferior de botones	
			JPanel pBotones = new JPanel();
			pBotones.setLayout(new FlowLayout());
			
			login = new JButton("Entrar");
			login.addActionListener(this);
			
			cancelar = new JButton("Cancelar");
			cancelar.addActionListener(this);
			
			pBotones.add(login);
			pBotones.add(cancelar);
		
		//Se agrega todo al panel principal y al JDialog	
		principal.add(interno,BorderLayout.NORTH);
		principal.add(pBotones,BorderLayout.SOUTH);
		this.add(principal);

		//Se establecen las configuraciones finales
		this.setPreferredSize(new Dimension (250,170));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==cancelar)
			System.exit(0);
		
		else {
		
			String contra = "";
			for (char letra : contraseña.getPassword())
				contra = contra + letra;
			
				try {
					conexion = LibreriaBaseDatos.conecta(bd, this.nombre.getText(), contra);
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				this.setVisible(false);
		}
		
	}

	public Connection getConexion() {
		return conexion;
	}
	
	
}
