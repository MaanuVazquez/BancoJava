package entidad.bancaria.excepciones;

public class CostoDeMantenimientoNoInicializadoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7454531108227526591L;

	public CostoDeMantenimientoNoInicializadoException() {
		super("El costo de mantenimiento no ha sido inicializado");
	}

}
