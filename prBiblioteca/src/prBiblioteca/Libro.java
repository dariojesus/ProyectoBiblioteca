package prBiblioteca;

public class Libro {
	
	private int codAutor;
	private int codLibro;
	private String autor;
	private String nacionalidad;
	private String titulo;
	private String publicacion;
	private String imagen;
	
	
	public Libro(int codAutor, int codLibro, String autor, String nacionalidad, String titulo, String publicacion, String imagen) {
		super();
		this.codAutor = codAutor;
		this.codLibro = codLibro;
		this.autor = autor;
		this.nacionalidad = nacionalidad;
		this.titulo = titulo;
		this.publicacion = publicacion;
		this.imagen=imagen;
	}
	
	public String toString() {
		return this.getTitulo();
		}


	public int getCodAutor() {
		return codAutor;
	}


	public void setCodAutor(int codAutor) {
		this.codAutor = codAutor;
	}


	public int getCodLibro() {
		return codLibro;
	}


	public void setCodLibro(int codLibro) {
		this.codLibro = codLibro;
	}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public String getNacionalidad() {
		return nacionalidad;
	}


	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getPublicacion() {
		return publicacion;
	}


	public void setPublicacion(String publicacion) {
		this.publicacion = publicacion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	
	

}
