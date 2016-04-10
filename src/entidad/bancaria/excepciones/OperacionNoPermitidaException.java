package entidad.bancaria.excepciones;

public class OperacionNoPermitidaException extends Exception {

	private static final long serialVersionUID = -4413417591838412866L;

	public OperacionNoPermitidaException() {
		super("La operación que intentó utilizar no está permitida para su tipo de cuenta");
	}

}
