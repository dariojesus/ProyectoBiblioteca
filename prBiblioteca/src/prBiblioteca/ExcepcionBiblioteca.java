package prBiblioteca;

public class ExcepcionBiblioteca extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExcepcionBiblioteca(){
		super();
	}
	
	public ExcepcionBiblioteca (String mensaje) {
		super(mensaje);
	}

}
