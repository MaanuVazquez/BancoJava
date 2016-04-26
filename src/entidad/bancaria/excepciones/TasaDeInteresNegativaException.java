package entidad.bancaria.excepciones;

public class TasaDeInteresNegativaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5320091864940582645L;
	
	public TasaDeInteresNegativaException(double tasaDeInteres) {
		
		super("La tasa de interes: " + tasaDeInteres + " no puede ser negativa.");
	}

}
