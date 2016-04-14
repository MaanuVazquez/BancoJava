package entidad.bancaria.excepciones;

public class SaldoInsuficienteException extends Exception {

	private static final long serialVersionUID = 2419444349123536423L;

	public SaldoInsuficienteException(int cbu, double monto, double saldo) {
		super("La cuenta N° " + cbu + " no dispone del saldo suficiente (" + saldo + "), para debitar " + monto + ".");
	}
}
