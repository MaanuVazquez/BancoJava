package entidad.bancaria.excepciones;

public class SaldoInsuficienteException extends Exception {

	private static final long serialVersionUID = 2419444349123536423L;

	public SaldoInsuficienteException() {
		super("Saldo insuficiente");
	}

}
