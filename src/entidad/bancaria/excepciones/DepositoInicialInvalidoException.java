package entidad.bancaria.excepciones;

public class DepositoInicialInvalidoException extends Exception {

	private static final long serialVersionUID = -7132063406216016662L;

	public DepositoInicialInvalidoException(double monto, double minimo) {
		super("El deposito inicial " + monto + " es invalido debe ser al menos " + minimo + ".");
	}
}
