package entidad.bancaria.excepciones;

public class SobregiroNegativoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5699374445136225113L;

	public SobregiroNegativoException(double sobregiro){
		super("El sobregiro: " + sobregiro + " no puede ser negativo");
	}

}
