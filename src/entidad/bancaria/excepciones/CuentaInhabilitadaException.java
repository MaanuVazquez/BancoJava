package entidad.bancaria.excepciones;

public class CuentaInhabilitadaException extends Exception {

	private static final long serialVersionUID = -4689323053298810130L;

	public CuentaInhabilitadaException(int cbu) {
		super("La cuenta N° : " + cbu + " esta inhabilitada para realizar operaciones.");
	}

}
