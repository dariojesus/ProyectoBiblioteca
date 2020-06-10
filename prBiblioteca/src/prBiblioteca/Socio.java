package prBiblioteca;

public class Socio {
	
	private int codSocio;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String direccion;
	
	public Socio(int codSocio, String nombre, String apellidos, String telefono, String direccion) {
		super();
		this.codSocio = codSocio;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	public String toString() {
		return this.getNombre()+" "+this.getApellidos()+" -Teléfono:"+this.getTelefono();
	}


	public int getCodSocio() {
		return codSocio;
	}


	public void setCodSocio(int codSocio) {
		this.codSocio = codSocio;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
