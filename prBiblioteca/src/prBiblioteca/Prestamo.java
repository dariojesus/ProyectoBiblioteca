package prBiblioteca;

public class Prestamo {
	
	private int codPrestamo;
	private String titulo;
	private String nombre;
	private String apellido;
	private String fecha;
	private String devolucion;
	
	public Prestamo(int codPrestamo,String titulo, String nombre, String apellido, String fecha, String devolucion) {
		super();
		this.codPrestamo = codPrestamo;
		this.titulo = titulo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fecha = fecha;
		this.devolucion = devolucion;
	}
	
	public int getCodPrestamo() {
		return codPrestamo;
	}

	public void setCodPrestamo(int codPrestamo) {
		this.codPrestamo = codPrestamo;
	}

	public String toString() {
		return this.getTitulo()+" -> "+this.getNombre()+" "+this.getApellido()+" ("+this.getFecha()+") ("+this.getDevolucion()+")";
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDevolucion() {
		return devolucion;
	}
	public void setDevolucion(String devolucion) {
		this.devolucion = devolucion;
	}
}
