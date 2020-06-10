package prBiblioteca;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;


public class DialogoLogin extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final String bd = "mibase";
	private static final long serialVersionUID = 1L;
	private JTextField nombre;
	private JPasswordField contrase�a;
	private JButton login,cancelar;
	private Connection conexion = null;
	
	public DialogoLogin() {
		this.setModal(true);
		this.setTitle("Autenticaci�n");
		this.setLayout(new BorderLayout(50,50));
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		
		JPanel principal = new JPanel();
		principal.setLayout(new BorderLayout());
		
			//Preparaci�n del panel compuesto de autentificaci�n
			JPanel interno = new JPanel();
			interno.setLayout(new GridLayout(2,2));
			
				//Preparaci�n de la etiqueta y panel para el nombre
				JLabel nom = new JLabel("Nombre: ");
				nom.setHorizontalAlignment(SwingConstants.RIGHT);
				interno.add(nom);
			
				JPanel pNombre = new JPanel();
				pNombre.setLayout(new FlowLayout());
				nombre = new JTextField(8);
				nombre.setText("dariojesus");
				
				pNombre.add(nombre);
				interno.add(pNombre);
				
				
				//Preparaci�n de la etiqueta y panel para contrase�a
				JLabel con = new JLabel("Contrase�a: ");
				con.setHorizontalAlignment(SwingConstants.RIGHT);
				interno.add(con);
				
				JPanel pContra = new JPanel();
				pContra.setLayout(new FlowLayout());
				contrase�a = new JPasswordField(8);
				contrase�a.setText("1234");
				
				pContra.add(contrase�a);
				interno.add(pContra);
				
			//Preparaci�n del panel inferior de botones	
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
		this.setLocationRelativeTo(null);
		this.setPreferredSize(new Dimension (250,140));
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
			for (char letra : contrase�a.getPassword())
				contra = contra + letra;
			
				try {
					conexion = LibreriaBaseDatos.conecta(bd, this.nombre.getText(), contra);
					this.setVisible(false);
				
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, "Ha ocurrido un eror al conectarse, revise su usuario y contrase�a","Error",JOptionPane.ERROR_MESSAGE);
				} 
				
				
		}
		
	}

	public Connection getConexion() {
		return conexion;
	}
	
	
}
