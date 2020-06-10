package prBiblioteca;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;


public class Modelo {
	
	private Connection conexion;
	private Statement st;
	private ResultSet res;
	
	//Constructor al que se le pasa la conexión del JDialog
	public Modelo (Connection con) {
		
		try {
			conexion = con;
			this.crearStatement();
			this.creaConsulta();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Métodos útiles del modelo
	public void cierraConexion() throws SQLException {
		conexion.close();
		System.out.println("Conexión cerrada");
		}
	
	public void creaConsulta() throws SQLException {res = st.executeQuery("SELECT * FROM AUTOR");}
	
	public void crearStatement() throws SQLException {st = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);}
	
	public void cierraStatement() throws SQLException {st.close();}
	
	
	//Acciones del panel libros
	
	public Libro crearLibro(String autor, String nacional, String titulo, String año, String imagen) throws SQLException, ExcepcionBiblioteca {
		
			if ((autor.isBlank() || nacional.isBlank()) || (titulo.isBlank() || año.isBlank()))
				throw new ExcepcionBiblioteca("Los campos autor, nacionalidad, titulo y año son obigatorios.");
			
			int codAutor = this.existeAutor(autor);
			int codUltimo = codAutor;
			int codLibro;
			
			if (codAutor == -1) {
				st.executeUpdate("INSERT INTO autor (nombre,nacionalidad) values ('"+autor+"','"+nacional+"')");
				codUltimo = this.existeAutor(autor);
			}
							
			st.executeUpdate("INSERT INTO libro (titulo,publicacion,codAutor,imagen) values ('"+titulo+"','"+año+"',"+codUltimo+",'"+imagen+"')");
			
			res = st.executeQuery("SELECT MAX(codLibro) from libro");
			res.next();
			codLibro = res.getInt(1);
			
			Libro l = new Libro(codUltimo,codLibro,autor,nacional,titulo,año,imagen);
			
			return l;

	}
	
	public String borrarLibro (Libro l) throws SQLException {
		
		st.executeUpdate("DELETE FROM libro WHERE codLibro = "+l.getCodLibro());
		this.creaConsulta();
		
		return "LIBRO: "+l.getTitulo()+"\nDEL AUTOR: "+l.getAutor()+"\nCON CÓDIGO: "+l.getCodLibro()+"\nBORRADO";
	}

	public int existeAutor(String autor){
		
		int cod = -1; 
		
		try {
			res = st.executeQuery("SELECT codAutor from autor where upper(nombre) ='"+autor+"'");
			res.next();
			cod = res.getInt(1);
			
		} catch (SQLException e) {}
		
		return cod;

	}
	
	//Acciones del panel socios
	
	public String borrarSocio(int codigo) throws SQLException {
		
		st.executeUpdate("DELETE FROM socio where codSocio ="+codigo);
		this.creaConsulta();
		
		return "Socio con código: "+codigo+" borrado con éxito";
	}
	
	public Socio creaSocio(int codigo, String nombre, String apellidos, String telefono, String direccion) throws SQLException, ExcepcionBiblioteca {
		
		if ((nombre.isBlank() || apellidos.isBlank()) || (telefono.isBlank() || direccion.isBlank()))
			throw new ExcepcionBiblioteca("Los campos nombre, apellidos, telefono y direccion son obligatorios.");
		
		//No ha entrado un código válido (campo en blanco) por lo cual se creará un nuevo usuario
		if (codigo ==-1) {
			st.executeUpdate("INSERT INTO socio (nombre,apellidos,telefono,direccion) values ('"+nombre+"','"+apellidos+"','"+telefono+"','"+direccion+"')");
			res = st.executeQuery("SELECT MAX(codSocio) from socio");
			res.next();
			codigo = res.getInt(1);
			
		//Ha entrado un código válido (el campo código tiene dato) por lo cual se esta editando un usuario ya existente
		}else {
			st.executeUpdate("UPDATE socio SET nombre='"+nombre+"', apellidos='"+apellidos+"', telefono='"+telefono+"', direccion='"+direccion+"'"
								+"WHERE codSocio="+codigo);
			
		}
		
		this.creaConsulta();

		return new Socio(codigo,nombre,apellidos,telefono,direccion);
		
	}
	
	
	//Acciones del panel prestamos

	public Prestamo prestarLibro(Libro l, Socio s) throws SQLException {
		//Para la fecha de hoy utilizamos una variable
		Calendar hoy = Calendar.getInstance();
		Date fecha = new Date(hoy.getTime().getTime());
		
		//Para la fecha de devolucio (1 mes) se lo agregamos a la de hoy
		hoy.add(Calendar.MONTH,1);
		Date fechaFin = new Date(hoy.getTime().getTime());
		
		st.executeUpdate("INSERT INTO mibase.prestamos (fecha_prestamo,fecha_devolucion,codSocio,codLibro) "
				+ "values ('"+fecha+"','"+fecha+"','"+s.getCodSocio()+"','"+l.getCodLibro()+"')");
			
		this.creaConsulta();
		
		res = st.executeQuery("SELECT MAX(codPrestamo) from prestamos");
		res.next();
		int cod = res.getInt(1);
		
		this.creaConsulta();
			
		return new Prestamo(cod,l.getTitulo(),s.getNombre(),s.getApellidos(),fecha.toString(),fechaFin.toString());
		
	}

	public String devolverLibro(Prestamo p) throws SQLException {
		
		st.executeUpdate("DELETE FROM mibase.prestamos where codPrestamo = "+p.getCodPrestamo());
		this.creaConsulta();
		
		return "LIBRO: "+p.getTitulo()+" \n"+
			    "PRESTADO A: "+p.getNombre()+" "+p.getApellido()+" \n"+
			    "HA SIDO DEVUELTO.";
		
	}

	//Getters y setters
	public Connection getConexion() {
		return conexion;
	}
	

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	

	public Statement getSt() {
		return st;
	}
	

	public void setSt(Statement st) {
		this.st = st;
	}
	

	public ResultSet getRes() {
		return res;
	}
	

	public void setRes(ResultSet res) {
		this.res = res;
	}
	
}


