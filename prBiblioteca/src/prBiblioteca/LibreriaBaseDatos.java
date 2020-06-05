package prBiblioteca;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class LibreriaBaseDatos {
	
	//Metodo para establecer la conexión con la base de datos
	public static Connection conecta(String base, String us, String clv) throws SQLException,Exception {
		
		// Registrar la conexión / Levantas el JDBC
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
					
		// Establecer la conexión
		String url = "jdbc:mysql://localhost:3306/"+base;
		Connection conexion = DriverManager.getConnection(url, us, clv);
					
		System.out.println("Conexión establecida");
					
		return conexion;
	}
	
	//Método para crear todas las tablas necesarias para la aplicacion
	public static void creaTablas(Connection conexion, String nombre) throws SQLException {
		
		Statement stmt = conexion.createStatement();
		
		//Se crea la tabla SOCIO
		String creaTabla = "create table " +nombre+ ".SOCIO " +
				"(codSocio INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
				"nombre varchar(50) NOT NULL, " +
				"apellidos varchar(50) NOT NULL, " +
				"telefono varchar(13) NOT NULL, " +
				"direccion varchar(50) NOT NULL) ";
		
		stmt.executeUpdate(creaTabla);
		System.out.println("Tabla SOCIO creada.");
		
		//Se crea la tabla AUTOR
		creaTabla = "create table " +nombre+ ".AUTOR " +
				"(codAutor INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
				"nombre varchar(50) NOT NULL UNIQUE, " +
				"nacionalidad varchar(50) NOT NULL) ";
		
		stmt.executeUpdate(creaTabla);
		System.out.println("Tabla AUTOR creada.");
		
		//Se crea la tabla LIBRO
		
		creaTabla = "create table " +nombre+ ".LIBRO " +
				"(codLibro INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
				"titulo varchar(50) NOT NULL, " +
				"publicacion varchar(5) NOT NULL, "+
				"codAutor INT NOT NULL, "+
				"imagen varchar(2083), "+
				"FOREIGN KEY (codAutor) REFERENCES AUTOR(codAutor), "+ 
				"UNIQUE KEY (titulo,codAutor))";
		
		stmt.executeUpdate(creaTabla);
		System.out.println("Tabla LIBRO creada.");
		
		
		//Se crea la tabla PRESTAMOS
		creaTabla = "create table " +nombre+ ".PRESTAMOS " +
				"(codPrestamo INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
				"fecha_prestamo Date NOT NULL, " +
				"fecha_devolucion Date NOT NULL, " +
				"codSocio INT NOT NULL, " +
				"codLibro INT NOT NULL, "+
				"FOREIGN KEY (codSocio) REFERENCES SOCIO(codSocio), "+
				"FOREIGN KEY (codLibro) REFERENCES LIBRO(codLibro))";
		
		stmt.executeUpdate(creaTabla);
		System.out.println("Tabla PRESTAMOS creada.");
		
		
		stmt.close();
		
	}
	
	//Método para rellenar todas las tablas a partir de sus respectivos ficheros
	public static void rellenaTablas(Connection conexion, String nombre) throws SQLException{
		
		Statement st = conexion.createStatement();
		
		
		//Se rellena la tabla de Socio
		try (Scanner sc = new Scanner (new File("./src/prBiblioteca/socios.txt"))){
			
			while(sc.hasNextLine()){
				Scanner datos = new Scanner(sc.nextLine());
				datos.useDelimiter(":");
				st.executeUpdate("insert into "+nombre+".SOCIO (nombre,apellidos,telefono,direccion) "
									+ "values ('"+datos.next()+"','"+datos.next()+"','"+datos.next()+"','"+datos.next()+"')");
				datos.close();
			}
			
		}catch (IOException e){e.printStackTrace();}
		
		System.out.println("Tabla SOCIO rellenada.");
		
		//Se rellena la tabla de Autor
		try (Scanner sc = new Scanner (new File("./src/prBiblioteca/autores.txt"))){
			
			while(sc.hasNextLine()){
				Scanner datos = new Scanner(sc.nextLine());
				datos.useDelimiter("-");
				st.executeUpdate("insert into "+nombre+".AUTOR (nombre,nacionalidad) values ('"+datos.next()+"','"+datos.next()+"')");
				datos.close();
			}
			
		}catch (IOException e){e.printStackTrace();}
		
		System.out.println("Tabla AUTOR rellenada.");
		
		//Se rellena la tabla de Libro
		try (Scanner sc = new Scanner (new File("./src/prBiblioteca/libros.txt"))){
			
			while(sc.hasNextLine()){
				Scanner datos = new Scanner(sc.nextLine());
				datos.useDelimiter("¬");
				st.executeUpdate("insert into "+nombre+".LIBRO (titulo,publicacion,codAutor,imagen) "
								+ "values ('"+datos.next()+"','"+datos.next()+"',"+datos.next()+",'"+datos.next()+"')");
				datos.close();
			}
			
		}catch (IOException e){e.printStackTrace();}
		
		System.out.println("Tabla LIBRO rellenada.");
		
		//Se rellena la tabla de PRESTAMOS
		try (Scanner sc = new Scanner (new File("./src/prBiblioteca/prestados.txt"))){
			
			while(sc.hasNextLine()){
				Scanner datos = new Scanner(sc.nextLine());
				datos.useDelimiter(":");
				st.executeUpdate("insert into "+nombre+".PRESTAMOS (fecha_prestamo,fecha_devolucion,codSocio,codLibro) "
						+ "values ('"+Date.valueOf(datos.next())+"','"+Date.valueOf(datos.next())+"',"+datos.next()+","+datos.next()+")");
				datos.close();
			}
			
		}catch (IOException e){e.printStackTrace();}
		
		System.out.println("Tabla PRESTAMOS rellenada.");
		
		st.close();
		
	}
	

}
