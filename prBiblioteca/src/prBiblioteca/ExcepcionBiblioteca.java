package prBiblioteca;

public class ExcepcionBiblioteca extends Exception {

	/**
	 * Creamos una clase para lanzar nuestras excepciones
	 */
	private static final long serialVersionUID = 1L;
	
	public ExcepcionBiblioteca(){
		super();
	}
	
	public ExcepcionBiblioteca (String mensaje) {
		super(mensaje);
	}

}
