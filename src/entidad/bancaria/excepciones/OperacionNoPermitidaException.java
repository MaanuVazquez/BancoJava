package entidad.bancaria.excepciones;

public class OperacionNoPermitidaException extends Exception {

	private static final long serialVersionUID = -4413417591838412866L;

	public OperacionNoPermitidaException() {
		super("La operaci�n que intent� utilizar no est� permitida para su tipo de cuenta");
	}

}
