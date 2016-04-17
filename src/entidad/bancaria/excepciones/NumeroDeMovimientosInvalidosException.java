package entidad.bancaria.excepciones;

public class NumeroDeMovimientosInvalidosException extends Exception {

	private static final long serialVersionUID = -1180750427636356356L;

	public NumeroDeMovimientosInvalidosException(int numeroDeMovimientos) {
		super("El numero de ultimos movimientos ingresado " + numeroDeMovimientos
				+ " es incorrecto. Debe ser al menos 1.");
	}
}
